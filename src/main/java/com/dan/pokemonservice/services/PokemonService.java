package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.PokemonDTO;

import java.util.List;

public interface PokemonService {

    List<PokemonDTO> getRandomPokemons(int amount);
}
