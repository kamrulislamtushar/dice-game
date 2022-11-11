package com.dice.game.service;

import com.dice.game.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    Player createPlayer(Player player);
    Page<Player> getAllPlayers(Pageable pageable);
}
