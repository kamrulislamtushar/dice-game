package com.dice.game.service;

import com.dice.game.dto.FinalScoreDetails;
import com.dice.game.dto.GamePointDto;

public interface GamePlayService {
    /**
     *
     * @param gamePointDto -> scoring point
     * @Description start game and calculate each player score;
     * @return
     */
    FinalScoreDetails playGame(GamePointDto gamePointDto);
}
