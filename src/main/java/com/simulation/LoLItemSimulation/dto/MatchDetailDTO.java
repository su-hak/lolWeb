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
        private String championName;
        private int kills;
        private int deaths;
        private int assists;
        private int championId;
        private int summoner1Id;
        private int summoner2Id;
        private int item0;
        private int item1;
        private int item2;
        private int item3;
        private int item4;
        private int item5;
        private int item6;
        private int totalDamageDealt;

        public String getSummonerName() {
            return summonerName;
        }

        public void setSummonerName(String summonrName) {
            this.summonerName = summonrName;
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

        public int getSummoner1Id() {
            return summoner1Id;
        }

        public void setSummoner1Id(int summoner1Id) {
            this.summoner1Id = summoner1Id;
        }

        public int getSummoner2Id() {
            return summoner2Id;
        }

        public void setSummoner2Id(int summoner2Id) {
            this.summoner2Id = summoner2Id;
        }

        public int getItem0() {
            return item0;
        }

        public void setItem0(int item0) {
            this.item0 = item0;
        }

        public int getItem1() {
            return item1;
        }

        public void setItem1(int item1) {
            this.item1 = item1;
        }

        public int getItem2() {
            return item2;
        }

        public void setItem2(int item2) {
            this.item2 = item2;
        }

        public int getItem3() {
            return item3;
        }

        public void setItem3(int item3) {
            this.item3 = item3;
        }

        public int getItem4() {
            return item4;
        }

        public void setItem4(int item4) {
            this.item4 = item4;
        }

        public int getItem5() {
            return item5;
        }

        public void setItem5(int item5) {
            this.item5 = item5;
        }

        public int getItem6() {
            return item6;
        }

        public void setItem6(int item6) {
            this.item6 = item6;
        }

        public int getTotalDamageDealt() {
            return totalDamageDealt;
        }

        public void setTotalDamageDealt(int totalDamageDealt) {
            this.totalDamageDealt = totalDamageDealt;
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
