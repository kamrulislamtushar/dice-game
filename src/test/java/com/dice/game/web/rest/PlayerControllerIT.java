package com.dice.game.web.rest;

import com.dice.game.dto.PlayerDto;
import com.dice.game.mapper.PlayerMapper;
import com.dice.game.model.Player;
import com.dice.game.repo.PlayerRepository;
import com.dice.game.util.IntegrationTest;
import com.dice.game.util.TestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.function.Consumer;

import static org.hamcrest.Matchers.hasItem;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@AutoConfigureMockMvc
@IntegrationTest
public class PlayerControllerIT {
    private static final String DEFAULT_NAME = "john";
    private static final String UPDATED_NAME = "doe";
    private static final Long DEFAULT_ID = 1L;
    private static final Integer DEFAULT_AGE = 20;
    private static final String ENTITY_API_URL = "/api/v1/players";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private PlayerMapper playerMapper;
    @Autowired
    private MockMvc restMockMvc;
    @Autowired
    private EntityManager em;
    private Player player;

    @BeforeEach
    public void initTest() {
        player = createEntity(em);
    }

    public static Player createEntity(EntityManager em) {
        Player player = new Player();
        player.setName(DEFAULT_NAME);
        player.setAge(DEFAULT_AGE);
        return player;
    }
    @Test
    @Transactional
    void createPlayer() throws Exception {
        int databaseSizeBeforeCreate = playerRepository.findAll().size();
        PlayerDto playerDto = new PlayerDto();
        playerDto.setName(DEFAULT_NAME);
        playerDto.setAge(DEFAULT_AGE);
        restMockMvc
                .perform(
                        post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(playerDto))
                )
                .andExpect(status().isCreated());
        assertPersistedPlayer(players -> {
            assertThat(players).hasSize(databaseSizeBeforeCreate + 1);
            Player testPlayer =  players.get(players.size() - 1);
            assertThat(testPlayer.getName()).isEqualTo(DEFAULT_NAME);
            assertThat(testPlayer.getAge()).isEqualTo(DEFAULT_AGE);
        });
    }

    @Test
    @Transactional
    void createProgramWithExistingId() throws Exception {
        // Create the Program with an existing ID
        player.setId(1L);

        int databaseSizeBeforeCreate = playerRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
                .andExpect(status().isBadRequest());

        // Validate the Program in the database
        List<Player> programList = playerRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeCreate);
    }

    private void assertPersistedPlayer(Consumer<List<Player>> userAssertion) {
        userAssertion.accept(playerRepository.findAll());
    }

    @Test
    @Transactional
    void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setName(null);

        // Create the Program, which fails.

        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
                .andExpect(status().isBadRequest());

        List<Player> programList = playerRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeTest);
    }


    @Test
    @Transactional
    void checkAgeIsRequired() throws Exception {
        int databaseSizeBeforeTest = playerRepository.findAll().size();
        // set the field null
        player.setAge(null);

        // Create the Program, which fails.
        restMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(player)))
                .andExpect(status().isBadRequest());

        List<Player> programList = playerRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get all the programList
        restMockMvc
                .perform(get(ENTITY_API_URL + "?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(player.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
                .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)));
    }


    @Test
    @Transactional
    void getPlayers() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        // Get the program
        restMockMvc
                .perform(get(ENTITY_API_URL_ID, player.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(player.getId().intValue()))
                .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
                .andExpect(jsonPath("$.age").value(DEFAULT_AGE));
    }

    @Test
    @Transactional
    void getNonExistingPlayer() throws Exception {
        // Get the program
        restMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }


    @Test
    @Transactional
    void deletePlayer() throws Exception {
        // Initialize the database
        playerRepository.saveAndFlush(player);

        int databaseSizeBeforeDelete = playerRepository.findAll().size();

        // Delete the program
        restMockMvc
                .perform(delete(ENTITY_API_URL_ID, player.getId()).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Player> programList = playerRepository.findAll();
        assertThat(programList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
