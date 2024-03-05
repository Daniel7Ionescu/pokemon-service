package com.dan.pokemonservice.utils;

import com.dan.pokemonservice.models.dtos.pokemon.PokemonDTO;
import com.dan.pokemonservice.models.entities.Pokemon;
import com.dan.pokemonservice.models.response.PokemonResponse;

public class PokemonMapper {

    public PokemonDTO responseToDto(PokemonResponse response) {
        return new PokemonDTO(
                response.getName(),
                response.getStats()[0].getBase_stat(),
                response.getStats()[1].getBase_stat(),
                response.getStats()[2].getBase_stat(),
                response.getSprites().getFront_default()
        );
    }

    public Pokemon dtoToEntity(PokemonDTO dto) {
        return new Pokemon(dto.getName(), dto.getHp(), dto.getAttack(), dto.getDefense(), dto.getSpriteUrl());
    }

    public PokemonDTO entityToDto(Pokemon entity) {
        return new PokemonDTO(entity.getName(), entity.getHp(), entity.getAttack(), entity.getDefense(), entity.getSpriteUrl());
    }


}
