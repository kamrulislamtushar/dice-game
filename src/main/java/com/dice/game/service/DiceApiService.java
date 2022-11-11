package com.dice.game.service;

import com.dice.game.dto.DiceDto;

public interface DiceApiService {
    /**
     * @param
     * @description API service to consume special dice
     * @return
     */
    DiceDto rollTheDice();
}
