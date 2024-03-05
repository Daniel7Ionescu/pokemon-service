package com.dan.pokemonservice.integration_tests;

import com.dan.pokemonservice.controllers.PokemonController;
import com.dan.pokemonservice.models.dtos.pokemon.PokemonCarrier;
import com.dan.pokemonservice.models.dtos.pokemon.PokemonDTO;
import com.dan.pokemonservice.services.PokemonService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PokemonController.class)
class PokemonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PokemonService pokemonService;

    @BeforeEach
    void setup() {
        PokemonDTO pokemonDTO = new PokemonDTO("poke1", 5, 6, 7, "sprite.com");
        PokemonCarrier myCarrier = new PokemonCarrier("Returned a random pokemon",pokemonDTO);

        when(pokemonService.getRandomPokemon()).thenReturn(myCarrier);
    }

    @Test
    void getRandomPokemon_ReturnsPokemonCarrier() throws Exception {
        PokemonDTO pokemonDTO = new PokemonDTO("poke1", 5, 6, 7, "sprite.com");
        PokemonCarrier myCarrier = new PokemonCarrier("Returned a random pokemon",pokemonDTO);

        MvcResult result = mvc.perform(MockMvcRequestBuilders
                        .get("/api/pokemons")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        PokemonCarrier pokemonCarrier = objectMapper.readValue(responseBody, PokemonCarrier.class);

        assertThat(pokemonCarrier).isNotNull();
        assertThat(pokemonCarrier.getMessage()).isEqualTo(myCarrier.getMessage());
    }
}
