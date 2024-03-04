package com.dan.pokemonservice.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "pokemons")
public class Pokemon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer hp;
    private Integer attack;
    private Integer defense;
    private String spriteUrl;

    public Pokemon() {
    }

    public Pokemon(String name, Integer hp, Integer attack, Integer defense, String spriteUrl) {
        this.name = name;
        this.hp = hp;
        this.attack = attack;
        this.defense = defense;
        this.spriteUrl = spriteUrl;
    }
}
