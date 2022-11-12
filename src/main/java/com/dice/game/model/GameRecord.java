package com.dice.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "game_records")
public class GameRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Min(10)
    @Max(Integer.MAX_VALUE)
    private Integer gamePoint;

    @OneToMany(fetch=FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval=true, mappedBy="gameRecord")
    private List<GameRecordDetails> details;



}
