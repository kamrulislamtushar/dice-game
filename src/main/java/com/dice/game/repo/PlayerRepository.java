package com.dice.game.repo;

import com.dice.game.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Override
    Page<Player> findAll(Pageable pageable);

    List<Player> findAllByOrderById();
}
