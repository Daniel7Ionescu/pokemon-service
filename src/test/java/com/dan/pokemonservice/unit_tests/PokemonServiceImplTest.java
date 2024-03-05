package com.dan.pokemonservice.unit_tests;

import com.dan.pokemonservice.models.dtos.pokemon.PokemonCarrier;
import com.dan.pokemonservice.models.dtos.pokemon.PokemonDTO;
import com.dan.pokemonservice.models.entities.Pokemon;
import com.dan.pokemonservice.models.response.PokemonResponse;
import com.dan.pokemonservice.models.response.Sprite;
import com.dan.pokemonservice.models.response.Stat;
import com.dan.pokemonservice.models.response.StatCategory;
import com.dan.pokemonservice.repositories.PokemonRepository;
import com.dan.pokemonservice.services.PokemonServiceImpl;
import com.dan.pokemonservice.utils.PokemonMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static com.dan.pokemonservice.utils.BusinessInfo.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@MockitoSettings(strictness = Strictness.LENIENT)
@ExtendWith(MockitoExtension.class)
class PokemonServiceImplTest {

    @Mock
    private PokemonRepository pokemonRepository;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PokemonMapper pokemonMapper;

    @Mock
    private Random random;

    @InjectMocks
    private PokemonServiceImpl pokemonService;

    @BeforeEach
    void setup() {

    }

    @Test
    void getRandomPokemon_ReturnsPokemonCarrier() {
        //given
        StatCategory statHp = new StatCategory(5, new Stat("hp"));
        StatCategory statAttack = new StatCategory(6, new Stat("attack"));
        StatCategory statDefense = new StatCategory(7, new Stat("defense"));
        Sprite spriteUrl = new Sprite("sprite.com");
        PokemonResponse pokemonResponse1 = new PokemonResponse(
                "poke1",
                new StatCategory[]{statHp, statAttack, statDefense},
                spriteUrl);
        Pokemon poke1 = new Pokemon("poke1", 5, 6, 7, "sprite.com");
        PokemonDTO pokemonDTO = new PokemonDTO("poke1", 5, 6, 7, "sprite.com");

        when(random.nextInt(MIN_POKEMON_ID, MAX_POKEMON_ID)).thenReturn(1);
        when(restTemplate.getForObject(POKEMON_URL + 1, PokemonResponse.class)).thenReturn(pokemonResponse1);
        when(pokemonMapper.responseToDto(pokemonResponse1)).thenReturn(pokemonDTO);
        when(pokemonMapper.dtoToEntity(pokemonDTO)).thenReturn(poke1);

        //when
        PokemonCarrier responseCarrier = pokemonService.getRandomPokemon();

        //then
        assertThat(responseCarrier.getPokemonDTO()).isNotNull();
        verify(pokemonRepository, description("PokemonRepository failed")).save(any(Pokemon.class));
    }

    @Test
    void getRandomPokemon_mapsCorrectly(){
        //given
        StatCategory statHp = new StatCategory(5, new Stat("hp"));
        StatCategory statAttack = new StatCategory(6, new Stat("attack"));
        StatCategory statDefense = new StatCategory(7, new Stat("defense"));
        Sprite spriteUrl = new Sprite("sprite.com");
        PokemonResponse pokemonResponse1 = new PokemonResponse(
                "poke1",
                new StatCategory[]{statHp, statAttack, statDefense},
                spriteUrl);
        Pokemon poke1 = new Pokemon("poke1", 5, 6, 7, "sprite.com");
        PokemonDTO pokemonDTO = new PokemonDTO("poke1", 5, 6, 7, "sprite.com");

        when(random.nextInt(MIN_POKEMON_ID, MAX_POKEMON_ID)).thenReturn(1);
        when(restTemplate.getForObject(POKEMON_URL + 1, PokemonResponse.class)).thenReturn(pokemonResponse1);
        when(pokemonMapper.responseToDto(pokemonResponse1)).thenReturn(pokemonDTO);
        when(pokemonMapper.dtoToEntity(pokemonDTO)).thenReturn(poke1);

        PokemonCarrier myCarrier = new PokemonCarrier("Returned a random pokemon",pokemonDTO);

        //when
        PokemonCarrier responseCarrier = pokemonService.getRandomPokemon();

        //then
        assertThat(responseCarrier).isNotNull();
        assertThat(responseCarrier.getMessage()).isEqualTo(myCarrier.getMessage());
        PokemonDTO resPokemon = responseCarrier.getPokemonDTO();
        assertThat(resPokemon.getName()).isEqualTo(myCarrier.getPokemonDTO().getName());
        assertThat(resPokemon.getHp()).isEqualTo(myCarrier.getPokemonDTO().getHp());
        assertThat(resPokemon.getAttack()).isEqualTo(myCarrier.getPokemonDTO().getAttack());
        assertThat(resPokemon.getDefense()).isEqualTo(myCarrier.getPokemonDTO().getDefense());
        assertThat(resPokemon.getSpriteUrl()).isEqualTo(myCarrier.getPokemonDTO().getSpriteUrl());
    }
}
