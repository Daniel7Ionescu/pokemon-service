package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.pokemon.PokemonCarrier;
import com.dan.pokemonservice.models.dtos.pokemon.PokemonDTO;
import com.dan.pokemonservice.models.entities.Pokemon;
import com.dan.pokemonservice.models.response.PokemonResponse;
import com.dan.pokemonservice.repositories.PokemonRepository;
import com.dan.pokemonservice.utils.PokemonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

import static com.dan.pokemonservice.utils.BusinessInfo.*;

@Slf4j
@Service
public class PokemonServiceImpl implements PokemonService {

    private final RestTemplate restTemplate;
    private final Random random;
    private final PokemonRepository pokemonRepository;
    private final PokemonMapper pokemonMapper;

    public PokemonServiceImpl(RestTemplate restTemplate, Random random, PokemonRepository pokemonRepository, PokemonMapper pokemonMapper) {
        this.restTemplate = restTemplate;
        this.random = random;
        this.pokemonRepository = pokemonRepository;
        this.pokemonMapper = pokemonMapper;
    }

    /**
     * Fetches a random pokemon, if it doesn't exist in the db, it will save it
     *
     * @return PokemonCarrier, holds a message and a PokemonDTO
     */
    @Override
    @Retryable(retryFor = RestClientException.class, maxAttempts = 2, backoff = @Backoff(delay = 200))
    public PokemonCarrier getRandomPokemon() {
        int generatedId = random.nextInt(MIN_POKEMON_ID, MAX_POKEMON_ID);
        PokemonResponse response = restTemplate.getForObject(POKEMON_URL + generatedId, PokemonResponse.class);
        PokemonDTO dto = pokemonMapper.responseToDto(response);

        if (pokemonRepository.findByName(dto.getName()).isEmpty()) {
            pokemonRepository.save(pokemonMapper.dtoToEntity(dto));
            log.info("Unique {} pokemon saved in db", dto.getName());
        }

        return new PokemonCarrier("Returned a random pokemon", dto);
    }

    /**
     * In case the pokemon fetching fails, attempts to retrieve a random pokemon from the database
     *
     * @return PokemonCarrier with a message and a random pokemon if any is available from the database
     */
    @Recover
    public PokemonCarrier recover(RestClientException e) {
        log.info(e.getMessage());
        PokemonCarrier carrier = new PokemonCarrier();
        long repositorySize = pokemonRepository.count();

        if (repositorySize > 0) {
            Pageable limit = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
            List<Pokemon> pokemonList = pokemonRepository.findAll(limit).getContent();
            int randomIndex = random.nextInt(0, pokemonList.size());
            PokemonDTO pokemonDTO = pokemonMapper.entityToDto(pokemonList.get(randomIndex));

            carrier.setMessage("Random pokemon retrieved from database");
            carrier.setPokemonDTO(pokemonDTO);
            log.info("Pokemon retrieved from database");
        } else {
            carrier.setMessage("No pokemons in database");
            log.info("No pokemons in database");
        }

        return carrier;
    }
}
