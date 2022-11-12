package com.dice.game.web.rest;

import com.dice.game.dto.FinalScoreDetails;
import com.dice.game.dto.GamePointDto;
import com.dice.game.service.GamePlayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/games")
public class GameController {
    private final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GamePlayService gamePlayService;
    public GameController(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @PostMapping("/roll/dice")
    public ResponseEntity<FinalScoreDetails> startGame(@Valid @RequestBody GamePointDto gamePointDto) {
        log.info("Request to start game for game point: {}", gamePointDto.toString());
        return ResponseEntity.ok().body(gamePlayService.playGame(gamePointDto));
    }

}
