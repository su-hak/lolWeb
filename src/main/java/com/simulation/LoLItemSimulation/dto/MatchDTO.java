package com.simulation.LoLItemSimulation.dto;

import java.util.List;


public class MatchDTO {
    private final MetadataDTO metadataDto;

    private final InfoDTO infoDTO;

    public MatchDTO(MetadataDTO metadataDto, InfoDTO infoDTO) {
        this.metadataDto = metadataDto;
        this.infoDTO = infoDTO;
    }

    public MetadataDTO getMetadataDto() {
        return metadataDto;
    }

    public InfoDTO getInfoDTO() {
        return infoDTO;
    }

}
