package com.dice.game.service.impl;

import com.dice.game.dto.DiceDto;
import com.dice.game.dto.FinalScoreDetailsDto;
import com.dice.game.dto.GamePointDto;
import com.dice.game.exception.BadRequestAlertException;
import com.dice.game.mapper.PlayerMapper;
import com.dice.game.model.GameRecord;
import com.dice.game.model.GameRecordDetails;
import com.dice.game.model.Player;
import com.dice.game.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class GamePlayServiceImpl implements GamePlayService {
    private final Logger log = LoggerFactory.getLogger(GamePlayServiceImpl.class);
    private final DiceApiService diceApiService;
    private final PlayerService playerService;
    private final GameRecordService gameRecordService;
    private final GameRecordDetailsService gameRecordDetailsService;
    private final PlayerMapper playerMapper;
    public GamePlayServiceImpl(
            DiceApiService diceApiService,
            PlayerService playerService,
            GameRecordService gameRecordService,
            GameRecordDetailsService gameRecordDetailsService,
            PlayerMapper playerMapper
    ) {
        this.diceApiService = diceApiService;
        this.playerService = playerService;
        this.gameRecordService = gameRecordService;
        this.gameRecordDetailsService = gameRecordDetailsService;
        this.playerMapper = playerMapper;
    }

    @Override
    public FinalScoreDetailsDto playGame(GamePointDto gamePointDto) {

        AtomicReference<Integer> maxScore = new AtomicReference<>(Integer.MIN_VALUE);
        List<Player> players = playerService.getAllPlayers();
        if (!CollectionUtils.isEmpty(players) && (players.size() <2 || players.size() > 4)) {
            throw new BadRequestAlertException("At least 2 and max 4 players needs to be on the board to start playing", "GAME", "shortOfPlayers");
        }
        if (getCurrentGame() != null) {
            throw new BadRequestAlertException("Already a game is running. Please wait!", "GAME", "shortOfPlayers");
        }
        log.info("Initialize game for gamePoint: {}", gamePointDto.getGamePoint());
        GameRecord gameRecord = initializeGame(gamePointDto, players);

        while (maxScore.get() <= gameRecord.getGamePoint()) {
            gameRecord.getDetails().forEach(gameRecordDetails -> {
               maxScore.set(rollTheDice(gameRecordDetails, maxScore.get()));
            });
        }
        log.info("Max score: {}", maxScore.get());
        GameRecordDetails gameRecordDetails = gameRecord.getDetails().stream().max(Comparator.comparingInt(GameRecordDetails::getCurrentScore)).get();
        FinalScoreDetailsDto finalScoreDetailsDto = new FinalScoreDetailsDto();
        finalScoreDetailsDto.setFinalScore(gameRecordDetails.getCurrentScore());
        finalScoreDetailsDto.setPlayerInfo(playerMapper.toDto(gameRecordDetails.getPlayer()));
        gameRecordService.deleteGameHistory();
        return finalScoreDetailsDto;
    }


    private Integer rollTheDice(GameRecordDetails gameRecordDetails, Integer maxScore) {
        DiceDto diceValue = this.diceApiService.rollTheDice();
        log.info("Player Name: {}, Total Score: {}, Current Value of Dice: {}", gameRecordDetails.getPlayer().getName(), gameRecordDetails.getCurrentScore(), diceValue.getScore());
        switch (diceValue.getScore()) {
            case 1,2,3,5: {
                if (!Objects.isNull(gameRecordDetails.getCurrentScore())) {
                    gameRecordDetails.setCurrentScore(gameRecordDetails.getCurrentScore() + diceValue.getScore());
                    maxScore = (Math.max(gameRecordDetails.getCurrentScore(), maxScore));
                    gameRecordDetailsService.saveScore(gameRecordDetails);
                }
                break;
            }
            case 6: {
                gameRecordDetails.setCurrentScore(Objects.isNull(gameRecordDetails.getCurrentScore()) ? 0 : gameRecordDetails.getCurrentScore() + 6);
                maxScore = (Math.max(gameRecordDetails.getCurrentScore(), maxScore));
                gameRecordDetailsService.saveScore(gameRecordDetails);
                rollTheDice(gameRecordDetails, maxScore);
                break;
            }
            case 4: {
                if (!Objects.isNull(gameRecordDetails.getCurrentScore())) {
                    if (gameRecordDetails.getCurrentScore() >= 4) {
                        gameRecordDetails.setCurrentScore(gameRecordDetails.getCurrentScore() - 4);
                    } else {
                        gameRecordDetails.setCurrentScore(0);
                    }
                    gameRecordDetailsService.saveScore(gameRecordDetails);
                    maxScore = (Math.max(gameRecordDetails.getCurrentScore(), maxScore));
                }
                break;
            }
        }
        return maxScore;
    }

    private GameRecord getCurrentGame() {
        log.info("Get current game record");
       return gameRecordService.fetchCurrentGame();
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

    @Override
    public List<GameRecordDetails> getCurrentScore() {
        log.info("Getting current score information");
        return gameRecordDetailsService.getAllPlayerScore();
    }

}
