package com.dice.game.service;

import com.dice.game.dto.FinalScoreDetails;
import com.dice.game.dto.GamePointDto;
import com.dice.game.model.GameRecordDetails;

import java.util.List;
public interface GamePlayService {
    /**
     * @param gamePointDto -> scoring point
     * @Description start game and calculate each player score;
     * @return
     */
    FinalScoreDetails playGame(GamePointDto gamePointDto);

    /**
     *
     * @Description get all players current score details
     * @return
     */
    List<GameRecordDetails> currentScore();
}
