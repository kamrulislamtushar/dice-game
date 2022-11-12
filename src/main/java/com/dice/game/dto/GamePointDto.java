package com.dice.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GamePointDto {
    @NotNull
    @Min(10)
    private Integer gamePoint;

    @Override
    public String toString() {
        return "GamePointDto{" +
                "gamePoint=" + gamePoint +
                '}';
    }
}
