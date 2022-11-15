package com.dice.game.service;

import com.dice.game.dto.FinalScoreDetailsDto;
import com.dice.game.dto.GamePointDto;
import com.dice.game.model.GameRecordDetails;

import java.util.List;
public interface GamePlayService {
    /**
     * @param gamePointDto -> scoring point
     * @Description start game and calculate each player score;
     * @return
     */
    FinalScoreDetailsDto playGame(GamePointDto gamePointDto);

    /**
     *
     * @Description get all players current score details
     * @return
     */
    List<GameRecordDetails> getCurrentScore();
}
