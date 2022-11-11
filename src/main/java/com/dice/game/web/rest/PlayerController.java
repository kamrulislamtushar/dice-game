package com.dice.game.web.rest;
import com.dice.game.dto.PlayerDto;
import com.dice.game.mapper.PlayerMapper;
import com.dice.game.model.Player;
import com.dice.game.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/players")
public class PlayerController {
    private final Logger log = LoggerFactory.getLogger(PlayerController.class);
    private final PlayerService playerService;
    private final PlayerMapper playerMapper;

    public PlayerController(
            PlayerService playerService,
            PlayerMapper playerMapper
    ) {
        this.playerService = playerService;
        this.playerMapper = playerMapper;
    }

    @PostMapping("")
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        log.info("Rest request to create player");
        Player player = playerMapper.toEntity(playerDto);
        player = playerService.createPlayer(player);
        return ResponseEntity.ok().body(playerMapper.toDto(player));
    }
    @GetMapping
    public ResponseEntity<Boolean> getAllPlayers() {
        return ResponseEntity.ok().body(true);
    }
}
