package com.dice.game.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiceDto {
    @JsonProperty("score")
    private Integer score;

    @Override
    public String toString() {
        return "DiceDto{" +
                "score=" + score +
                '}';
    }
}
