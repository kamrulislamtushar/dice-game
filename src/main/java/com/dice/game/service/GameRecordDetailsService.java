package com.dice.game.service;
import com.dice.game.model.GameRecordDetails;

public interface GameRecordDetailsService {
    GameRecordDetails saveScore(GameRecordDetails gameRecordDetails);
}
