package com.simulation.LoLItemSimulation.dto;

import java.util.List;

public class MatchDetailDTO {
    private Info info;

    public Info getInfo() {
        return info;
    }

    public void setInfo() {
        this.info = info;
    }

    public static class Info {
        private List<Participant> participants;

        public List<Participant> getParticipants() {
            return participants;
        }

        public void setParticipants(List<Participant> participants) {
            this.participants = participants;
        }
    }

    public static class Participant {
        private String summonerName;
        private String riotIdTagline;
        private String championName;
        private int kills;
        private int deaths;
        private int assists;
        private int championId;
        private int totalDamageDealt;
        private int participantId;
        private String puuid;

        public String getSummonerName() {
            return summonerName;
        }

        public void setSummonerName(String summonrName) {
            this.summonerName = summonrName;
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
    }
    private String matchId;
    private List<ParticipantDTO> participants;
    private String teams;
    private String gameDuration;
    private String gameMode;
    private String gameType;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    public String getTeams() {
        return teams;
    }

    public void setTeams(String teams) {
        this.teams = teams;
    }

    public String getGameDuration() {
        return gameDuration;
    }

    public void setGameDuration(String gameDuration) {
        this.gameDuration = gameDuration;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }
}
