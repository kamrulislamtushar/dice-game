package com.dice.game.service.impl;

import com.dice.game.dto.PlayerDto;
import com.dice.game.mapper.PlayerMapper;
import com.dice.game.model.Player;
import com.dice.game.repo.PlayerRepository;
import com.dice.game.service.PlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PlayerServiceImpl implements PlayerService {

    private final Logger log = LoggerFactory.getLogger(PlayerServiceImpl.class);

    private final PlayerRepository playerRepository;
    public PlayerServiceImpl(
            PlayerRepository playerRepository
    ) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player createPlayer(Player player) {
        player =  playerRepository.save(player);
        log.info("Player created with id: {}", player.getId());
        return  player;
    }

    @Override
    public Page<Player> getAllPlayers(Pageable pageable) {
       return playerRepository.findAll(pageable);
    }

    @Override
    public Optional<Player> getPlayer(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        playerRepository.deleteById(id);
    }
}
