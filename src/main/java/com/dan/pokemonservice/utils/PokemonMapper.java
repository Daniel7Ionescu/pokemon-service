package com.dan.pokemonservice.utils;

import com.dan.pokemonservice.models.dtos.PokemonDTO;
import com.dan.pokemonservice.models.response.PokemonResponse;

public class PokemonMapper {

    public static PokemonDTO toDto(PokemonResponse response) {
        return new PokemonDTO(
                response.getName(),
                response.getStats()[0].getBase_stat(),
                response.getStats()[1].getBase_stat(),
                response.getStats()[2].getBase_stat(),
                response.getSprites().getFront_default()
        );
    }
}
