package com.dice.game.web.rest;

import com.dice.game.dto.FinalScoreDetailsDto;
import com.dice.game.dto.GamePointDto;
import com.dice.game.model.GameRecordDetails;
import com.dice.game.service.GamePlayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/games")
@CrossOrigin("*")
public class GameController {
    private final Logger log = LoggerFactory.getLogger(GameController.class);
    private final GamePlayService gamePlayService;
    public GameController(GamePlayService gamePlayService) {
        this.gamePlayService = gamePlayService;
    }

    @PostMapping("/roll/dice")
    public ResponseEntity<FinalScoreDetailsDto> startGame(@Valid @RequestBody GamePointDto gamePointDto) {
        log.info("REST Request to start game for game point: {}", gamePointDto.toString());
        return ResponseEntity.ok().body(gamePlayService.playGame(gamePointDto));
    }

    @GetMapping("/points")
    public ResponseEntity<List<GameRecordDetails>> getAllPlayerScore() {
        log.info("REST Request to get players score");
        return ResponseEntity.ok().body(gamePlayService.getCurrentScore());
    }

}
