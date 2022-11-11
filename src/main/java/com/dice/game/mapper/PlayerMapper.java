package com.dice.game.mapper;

import com.dice.game.dto.PlayerDto;
import com.dice.game.model.Player;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PlayerMapper implements EntityMapper<PlayerDto, Player> {

    public PlayerDto toDto(Player player) {
        return new PlayerDto(player);
    }

    public Player toEntity(PlayerDto playerDto) {
        Player player = new Player();
        player.setId(playerDto.getId());
        player.setName(playerDto.getName());
        player.setAge(playerDto.getAge());
        return player;
    }

    public List<PlayerDto> toDto(List<Player> players) {
        return players
                .stream()
                .filter(Objects::nonNull)
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<Player> toEntity(List<PlayerDto> playerDtos) {
        return playerDtos
                .stream()
                .filter(Objects::nonNull)
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}
