package com.dice.game.service;
import com.dice.game.model.GameRecordDetails;
import java.util.List;

public interface GameRecordDetailsService {
    /**
     * @description update game record details after each dice roll
     * @param gameRecordDetails
     * @return
     */
    GameRecordDetails saveScore(GameRecordDetails gameRecordDetails);

    /**
     *
     * @description get score of all players;
     * @param
     * @return List<GameRecordDetails>
     */
    List<GameRecordDetails> getAllPlayerScore();
}
