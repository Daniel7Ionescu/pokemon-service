package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.PokemonCarrier;

import java.util.List;

public interface PokemonService {

    PokemonCarrier getRandomPokemons(int amount);
}
