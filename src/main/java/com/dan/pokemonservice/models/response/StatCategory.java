package com.dan.pokemonservice.models.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatCategory {

    private Integer base_stat;
    private Stat stat;


}
