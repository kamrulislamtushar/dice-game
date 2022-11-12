package com.dice.game.service;

import com.dice.game.model.GameRecord;

public interface GameRecordService {
    /**
     *
     * @description save initial game state to database
     * @param gameRecord
     * @return
     */
    GameRecord saveInitialGame(GameRecord gameRecord);

    /**
     *
     * @description get current running game state
     * @return
     */
    GameRecord fetchCurrentGame();

    /**
     *
     * @description delete game history after game game completion
     */
    void deleteGameHistory();
}
