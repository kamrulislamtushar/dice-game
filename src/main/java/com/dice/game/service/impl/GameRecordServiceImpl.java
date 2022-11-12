package com.dice.game.service.impl;

import com.dice.game.model.GameRecord;
import com.dice.game.repo.GameRecordRepository;
import com.dice.game.service.GameRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameRecordServiceImpl implements GameRecordService {

    private final GameRecordRepository gameRecordRepository;
    public GameRecordServiceImpl(GameRecordRepository gameRecordRepository) {
        this.gameRecordRepository = gameRecordRepository;
    }
    @Transactional
    public GameRecord saveInitialGame(GameRecord gameRecord) {
        return gameRecordRepository.save(gameRecord);
    }

    public GameRecord fetchCurrentGame() {
        return gameRecordRepository.findFirstByOrderById();
    }
}
