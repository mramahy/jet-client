package com.jet.client.model;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
public class GameDto implements Serializable {

    private GameState state;
    private String id;
    private Integer number;
    private GameMode mode;
}