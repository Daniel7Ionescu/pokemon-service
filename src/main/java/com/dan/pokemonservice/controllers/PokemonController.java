package com.dan.pokemonservice.controllers;

import com.dan.pokemonservice.models.dtos.pokemon.PokemonCarrier;
import com.dan.pokemonservice.services.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<PokemonCarrier> getRandomPokemon() {
        return ResponseEntity.ok(pokemonService.getRandomPokemon());
    }
}
