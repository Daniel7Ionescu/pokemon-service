package com.dan.pokemonservice.controllers;

import com.dan.pokemonservice.models.dtos.PokemonDTO;
import com.dan.pokemonservice.services.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pokemons")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping
    public ResponseEntity<List<PokemonDTO>> getRandomPokemons(@RequestParam(defaultValue = "3") Integer amount) {
        return ResponseEntity.ok(pokemonService.getRandomPokemons(amount));
    }
}
