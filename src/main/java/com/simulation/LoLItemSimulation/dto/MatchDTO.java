package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;

import java.util.List;


public class MatchDTO {
    private final MetadataDTO metadata;

    private final InfoDTO info;

    public MatchDTO(MetadataDTO metadata, InfoDTO info) {
        this.metadata = metadata;
        this.info = info;
    }

    public MetadataDTO getMetadata() {
        return metadata;
    }

    public InfoDTO getInfo() {
        return info;
    }
}
