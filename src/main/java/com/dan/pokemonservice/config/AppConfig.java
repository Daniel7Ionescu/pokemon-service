package com.dan.pokemonservice.config;

import com.dan.pokemonservice.utils.PokemonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public PokemonMapper pokemonMapper() {
        return new PokemonMapper();
    }
}
