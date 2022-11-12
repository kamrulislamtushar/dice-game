package com.dice.game.service.impl;

import com.dice.game.model.GameRecordDetails;
import com.dice.game.repo.GameRecordDetailsRepository;
import com.dice.game.service.GameRecordDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class GameRecordDetailsServiceImpl implements GameRecordDetailsService {

    private final Logger log = LoggerFactory.getLogger(GameRecordDetailsServiceImpl.class);
    private final GameRecordDetailsRepository gameRecordDetailsRepository;

    public GameRecordDetailsServiceImpl(GameRecordDetailsRepository gameRecordDetailsRepository) {
        this.gameRecordDetailsRepository = gameRecordDetailsRepository;
    }
    public GameRecordDetails saveScore(GameRecordDetails gameRecordDetails) {
        log.info("Persisting record to DB");
        return gameRecordDetailsRepository.save(gameRecordDetails);
    }
    public List<GameRecordDetails> getAllPlayerScore() {
        return gameRecordDetailsRepository.findAll();
    }
}
