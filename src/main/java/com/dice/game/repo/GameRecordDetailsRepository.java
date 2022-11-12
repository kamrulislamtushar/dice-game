package com.dice.game.repo;
import com.dice.game.model.GameRecordDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRecordDetailsRepository extends JpaRepository<GameRecordDetails, Long> {

}
