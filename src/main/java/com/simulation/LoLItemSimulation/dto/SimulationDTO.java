package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SimulationDTO {
    private Long id;
    private Long postId;
    private String championName;
    private String item1;
    private String item2;
    private String item3;
    private String item4;
    private String item5;
    private String item6;
}
