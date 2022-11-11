package com.dice.game.dto;

import com.dice.game.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerDto {
    private Long id;
    private String name;
    private Integer age;


    public PlayerDto(Player player) {
        this.id = player.getId();
        this.name = player.getName();
        this.age = player.getAge();
    }

    @Override
    public String toString() {
        return "PlayerDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
