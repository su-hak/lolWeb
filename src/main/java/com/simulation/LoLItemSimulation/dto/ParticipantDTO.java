package com.simulation.LoLItemSimulation.dto;

import java.util.List;

public class ParticipantDTO {
        private String summonerName;
        private String riotIdGameName;
        private String riotIdTagline;
        private String championName;
        private int kills;
        private int deaths;
        private int assists;
        private int championId;
        private int totalDamageDealt;
        private int participantId;
        private String puuid;
        private PerksDTO perks;

        public String getSummonerName() {
            return summonerName;
        }

        public void setSummonerName(String summonrName) {
            this.summonerName = summonrName;
        }

        public String getRiotIdGameName() {
        return riotIdGameName;
        }

        public void setRiotIdGameName(String riotIdGameName) {
        this.riotIdGameName = riotIdGameName;
        }

    public String getRiotIdTagline() {
            return riotIdTagline;
        }

        public void setRiotIdTagline(String riotIdTagline) {
            this.riotIdTagline = riotIdTagline;
        }

        public String getChampionName() {
            return championName;
        }

        public void setChampionName(String championName) {
            this.championName = championName;
        }

        public int getKills() {
            return kills;
        }

        public void setKills(int kills) {
            this.kills = kills;
        }

        public int getDeaths() {
            return deaths;
        }

        public int getAssists() {
            return assists;
        }

        public void setAssists(int assists) {
            this.assists = assists;
        }

        public void setDeaths(int deaths) {
            this.deaths = deaths;
        }

        public int getChampionId() {
            return championId;
        }

        public void setChampionId(int championId) {
            this.championId = championId;
        }

        public int getTotalDamageDealt() {
            return totalDamageDealt;
        }

        public void etTotalDamageDealt(int totalDamageDealt) {
            this.totalDamageDealt = totalDamageDealt;
        }

        public void setTotalDamageDealt(int totalDamageDealt) {
            this.totalDamageDealt = totalDamageDealt;
        }

        public int getParticipantId() {
            return participantId;
        }

        public void setParticipantId(int participantId) {
            this.participantId = participantId;
        }

        public String getPuuid() {
            return puuid;
        }

        public void setPuuid(String puuid) {
            this.puuid = puuid;
        }

        public PerksDTO getPerks() {
            return perks;
        }

        public void setPerks(PerksDTO perks) {
            this.perks = perks;
        }

}

