package com.dan.pokemonservice.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonCarrier {

    private String message;
    private List<PokemonDTO> pokemons;
}
