package com.dan.pokemonservice.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PokemonDTO {

    private String name;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private String spriteUrl;
}
