package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.PokemonCarrier;
import com.dan.pokemonservice.models.dtos.PokemonDTO;
import com.dan.pokemonservice.models.entities.Pokemon;
import com.dan.pokemonservice.models.response.PokemonResponse;
import com.dan.pokemonservice.repositories.PokemonRepository;
import com.dan.pokemonservice.utils.PokemonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
public class PokemonServiceImpl implements PokemonService {

    private final RestTemplate restTemplate;
    private final Random random;
    private final PokemonRepository pokemonRepository;

    public PokemonServiceImpl(RestTemplate restTemplate, Random random, PokemonRepository pokemonRepository) {
        this.restTemplate = restTemplate;
        this.random = random;
        this.pokemonRepository = pokemonRepository;
    }

    /**
     * Fetches a list of random pokemons, saves unique pokemons in the db
     * @param amount the number of pokemons
     * @return PokemonCarrier, holds a message and a list of PokemonDTO
     */

    @Retryable(retryFor = RestClientException.class, maxAttempts = 2, backoff = @Backoff(delay = 200))
    @Override
    public PokemonCarrier getRandomPokemons(int amount) {
        int[] pokemonIds = generateRandomPokemonIds(amount);
        List<PokemonDTO> pokemonList = new ArrayList<>();

        Arrays.stream(pokemonIds)
                .forEach(id -> {
                    PokemonResponse response = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id, PokemonResponse.class);
                    pokemonList.add(PokemonMapper.responseToDto(response));
                });

        List<Pokemon> pokemonEntities = pokemonList.stream()
                .map(PokemonMapper::dtoToEntity)
                .filter(pokemon -> pokemonRepository.findByName(pokemon.getName()).isEmpty())
                .toList();
        pokemonRepository.saveAll(pokemonEntities);

        return new PokemonCarrier(String.format("Returned %d pokemons at random", amount), pokemonList);
    }

    /**
     * In case the pokemon fetching fails, attempts to retrieve a number of pokemons from the database
     * @param amount the requested amount of pokemons
     * @return PokemonCarrier with a message and a list of pokemons if any are available in database
     */
    @Recover
    public PokemonCarrier recover(int amount){
        PokemonCarrier carrier = new PokemonCarrier();
        Pageable limit = PageRequest.of(0,amount);
        List<Pokemon> retrievedPokemons = pokemonRepository.findAll(limit).getContent();

        if(retrievedPokemons.isEmpty()){
            carrier.setMessage("No pokemons in database");
            log.info("NO pokemons in database");
        }
        else {
            carrier.setMessage("Pokemons retrieved from database");
            carrier.setPokemons(retrievedPokemons.stream()
                            .map(PokemonMapper::entityToDto)
                            .toList());
            log.info("Pokemons retrieved from database");
        }

        return carrier;
    }

    /**
     * Genererates an array of random pokemon ids from 1 up to 50
     * @param amount determines how many ints will be generated
     * @return an array of random ints
     */
    private int[] generateRandomPokemonIds(int amount) {
        int[] generatedIds = new int[amount];
        while (amount > 0) {
            amount--;
            int randomId = random.nextInt(1, 50);
            generatedIds[amount] = randomId;
        }

        return generatedIds;
    }
}
