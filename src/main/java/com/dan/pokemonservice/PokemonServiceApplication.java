package com.dan.pokemonservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class PokemonServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PokemonServiceApplication.class, args);
	}

}
