package com.simulation.LoLItemSimulation.dto;

import lombok.*;

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
    private String championImageUrl;
    private String item1ImageUrl;
    private String item2ImageUrl;
    private String item3ImageUrl;
    private String item4ImageUrl;
    private String item5ImageUrl;
    private String item6ImageUrl;
}
