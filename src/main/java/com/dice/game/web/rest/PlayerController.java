package com.dice.game.web.rest;

import com.dice.game.dto.PlayerDto;
import com.dice.game.exception.BadRequestAlertException;
import com.dice.game.mapper.PlayerMapper;
import com.dice.game.model.Player;
import com.dice.game.service.PlayerService;
import com.dice.game.utility.HeaderUtil;
import com.dice.game.utility.PaginationUtil;
import com.dice.game.utility.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final Logger log = LoggerFactory.getLogger(PlayerController.class);
    private static final String ENTITY_NAME = "PLAYER";
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(
            PlayerService playerService,
            PlayerMapper playerMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) throws URISyntaxException {
        log.info("REST request to create player");
        if(playerDto.getId() !=  null) {
            throw new BadRequestAlertException("A new player cannot have an ID", ENTITY_NAME, "idexists");
        }
        Player player = playerMapper.toEntity(playerDto);
        player = playerService.createPlayer(player);
        return ResponseEntity
                .created(new URI("api/v1/players/" + player.getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, player.getId().toString()))
                .body(playerMapper.toDto(player));
    }
    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayers(Pageable pageable) {
        log.info("REST request to get all players");
        Page<Player> players = playerService.getAllPlayers(pageable);
        HttpHeaders httpHeaders = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequestUri(), players);
        return ResponseEntity.ok().headers(httpHeaders).body(players.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PlayerDto> getPlayer(@PathVariable Long id) {
        log.info("REST request to get player by id: {}", id);
        Optional<PlayerDto> player = playerService.getPlayer(id).map(playerMapper::toDto);
        return ResponseUtil.wrapOrNotFound(player);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removePlayer(@PathVariable Long id) {
        log.info("REST request to delete player by id: {}", id);
        playerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert("PLAYER", id.toString())).build();
    }
}
