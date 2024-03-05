package com.dan.pokemonservice.services;

import com.dan.pokemonservice.models.dtos.pokemon.PokemonCarrier;

public interface PokemonService {

    PokemonCarrier getRandomPokemon();
}
