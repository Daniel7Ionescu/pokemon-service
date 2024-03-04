package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.PokemonDTO;
import com.dan.pokemonservice.models.entities.Pokemon;
import com.dan.pokemonservice.models.response.PokemonResponse;
import com.dan.pokemonservice.utils.PokemonMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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

    public PokemonServiceImpl(RestTemplate restTemplate, Random random) {
        this.restTemplate = restTemplate;
        this.random = random;
    }

    @Override
    public List<PokemonDTO> getRandomPokemons(int amount) {
        int[] pokemonIds = generateRandomPokemonIds(amount);
        List<PokemonDTO> pokemonList = new ArrayList<>();

        Arrays.stream(pokemonIds)
                .forEach(id -> {
                    PokemonResponse response = restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/" + id, PokemonResponse.class);
                    pokemonList.add(PokemonMapper.toDto(response));
                });


        return pokemonList;
    }

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
