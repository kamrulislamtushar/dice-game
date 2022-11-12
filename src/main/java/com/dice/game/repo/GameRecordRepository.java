package com.dice.game.repo;

import com.dice.game.model.GameRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {
    GameRecord findFirstByOrderById();
}
