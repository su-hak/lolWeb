package com.simulation.LoLItemSimulation.dto;


// summoner-V4 에서 puuid가져오기

import java.util.List;

public class SummonerDTO {
    private String id;
    private String accountId;
    private String puuid;
    private String name;
    private int profileIconId;
    private long revisionDate;
    private long summonerLevel;

    public String getPuuid() {
        return puuid;
    }

    private List<String> matchIds;

    public List<String> getMatchIds() {
        return matchIds;
    }


    @Override
    public String toString() {
        return "SummonerDTO{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", puuid='" + puuid + '\'' +
                ", name='" + name + '\'' +
                ", profileIconId=" + profileIconId +
                ", revisionDate=" + revisionDate +
                ", summonerLevel=" + summonerLevel +
                ", matchIds=" + matchIds +
                '}';
    }
}
