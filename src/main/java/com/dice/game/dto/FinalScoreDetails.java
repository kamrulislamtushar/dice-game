package com.dice.game.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinalScoreDetails {
    private Integer finalScore;
    private PlayerDto playerInfo;
}
