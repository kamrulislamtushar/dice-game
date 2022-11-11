package com.dice.game.service;

import com.dice.game.model.Player;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface PlayerService {
    /**
     * @param player
     * @Description create a new player
     * @return player
     */
    Player createPlayer(Player player);

    /**
     * @param pageable
     * @Description get list of players
     * @return page of player
     */
    Page<Player> getAllPlayers(Pageable pageable);

    /**
     * @param id
     * @Description get player by id
     * @return player
     */
    Optional<Player> getPlayer(Long id);

    /**
     * @param id
     * @Description delete player by id
     * @return
     */
    void delete(Long id);
}
