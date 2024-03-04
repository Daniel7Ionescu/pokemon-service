package com.dan.pokemonservice.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PokemonResponse {

    private String name;
    private StatCategory[] stats;
    private Sprite sprites;
}
