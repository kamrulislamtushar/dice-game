package com.dice.game.service.impl;

import com.dice.game.dto.FinalScoreDetails;
import com.dice.game.dto.GamePointDto;
import com.dice.game.exception.BadRequestAlertException;
import com.dice.game.model.GameRecord;
import com.dice.game.model.GameRecordDetails;
import com.dice.game.model.Player;
import com.dice.game.repo.PlayerRepository;
import com.dice.game.service.DiceApiService;
import com.dice.game.service.GamePlayService;
import com.dice.game.service.GameRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GamePlayServiceImpl implements GamePlayService {
    private final Logger log = LoggerFactory.getLogger(GamePlayServiceImpl.class);
    private final DiceApiService diceApiService;
    private final PlayerRepository playerRepository;
    private final GameRecordService gameRecordService;
    public GamePlayServiceImpl(
            DiceApiService diceApiService,
            PlayerRepository playerRepository,
            GameRecordService gameRecordService
    ) {
        this.diceApiService = diceApiService;
        this.playerRepository = playerRepository;
        this.gameRecordService = gameRecordService;
    }

    @Override
    public FinalScoreDetails playGame(GamePointDto gamePointDto) {
        List<Player> players = getAllPlayers();
        if (players.size() <2 || players.size() > 4) {
            throw new BadRequestAlertException("At least 2 and max 4 players needs to be on the board to start playing", "GAME", "shortOfPlayers");
        }
        if (getCurrentGame() != null) {
            throw new BadRequestAlertException("Already a game is running. Please wait!", "GAME", "shortOfPlayers");
        }

        return null;
    }

    private Boolean checkIfGameCanBePlayed() {
        return playerRepository.findAll().size() >= 2;
    }
    private GameRecord getCurrentGame() {
       return gameRecordService.fetchCurrentGame();
    }
    private List<Player>  getAllPlayers() {
        return playerRepository.findAllByOrderById();
    }

    private GameRecord initializeGame(GamePointDto gamePointDto, List<Player> players) {
        GameRecord gameRecord = new GameRecord();
        gameRecord.setGamePoint(gamePointDto.getGamePoint());
        gameRecord.setDetails(new ArrayList<>());

        players.forEach(player -> {
            GameRecordDetails details = new GameRecordDetails();
            details.setPlayer(player);
            details.setGameRecord(gameRecord);
            gameRecord.getDetails().add(details);
        });
      return gameRecordService.saveInitialGame(gameRecord);
    }
}
