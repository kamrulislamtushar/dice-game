package com.dice.game.service;

import com.dice.game.model.GameRecord;

public interface GameRecordService {
    GameRecord saveInitialGame(GameRecord gameRecord);
    GameRecord fetchCurrentGame();
    void deleteGameHistory();
}
