// LeagueEntryDTO.java
package com.simulation.LoLItemSimulation.dto;


import java.util.List;

public class LeagueEntryDTO {
    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String queueType;
    private String tier;
    private String rank;
    private int leaguePoints;
    private int wins;
    private int losses;

    // summoner-V4 에서 puuid가져오기
    private String puuid;

    public void setPuuid(String puuid) {
        this.puuid = puuid;
    }
    public String getPuuid() {
        return puuid;
    }

    // matchID 가져오기
    private List<String> matchIds;

    public List<String> getMatchIds() {
        return matchIds;
    }
    public void setMatchIds(List<String> matchIds) {
        this.matchIds = matchIds;
    }


    // MATCH-V5 matchId가 필요
    private List<MatchDetailDTO.Participant> participants;
    private int participantId;
   /* private List<MatchTimelineDTO.ItemTimeline> itemTimelines;*/
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    private String championName;
    private int kills;
    private int deaths;
    private int assists;
    private String teamPosition;
    private List<Integer> championId;
    private int championPoints;
    private int summoner1Id;
    private int summoner2Id;
    private int totalDamageDealt;

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

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public String getTeamPosition() {
        return teamPosition;
    }

    public void setTeamPosition(String teamPosition) {
        this.teamPosition = teamPosition;
    }

    public String getLeagueId() {
        return leagueId;
    }

    public void setLeagueId(String leagueId) {
        this.leagueId = leagueId;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public String getQueueType() {
        return queueType;
    }

    public void setQueueType(String queueType) {
        this.queueType = queueType;
    }

    public String getTier() {
        return tier;
    }

    public void setTier(String tier) {
        this.tier = tier;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getLeaguePoints() {
        return leaguePoints;
    }

    public void setLeaguePoints(int leaguePoints) {
        this.leaguePoints = leaguePoints;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public List<Integer> getChampionId() {
        return championId;
    }

    public void setChampionId(List<Integer> championId) {
        this.championId = championId;
    }

    public int getChampionPoints() {
        return championPoints;
    }

    public void setChampionPoints(int championPoints) {
        this.championPoints = championPoints;
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

    public int getTotalDamageDealt() {
        return totalDamageDealt;
    }

    public void setTotalDamageDealt(int totalDamageDealt) {
        this.totalDamageDealt = totalDamageDealt;
    }

    public List<MatchDetailDTO.Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<MatchDetailDTO.Participant> participants) {

        this.participants = participants;
    }
/*
    public List<MatchTimelineDTO.ItemTimeline> getItemTimelines() {
        return itemTimelines;
    }

    public void setItemTimelines(List<MatchTimelineDTO.ItemTimeline> itemTimelines) {
        this.itemTimelines = itemTimelines;
    }*/

    private int itemId;
    private int timestamp;
    private String type;
    private String levelUpType;
    private int skillSlot;
    private String wardType;

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLevelUpType() {
        return levelUpType;
    }

    public void setLevelUpType(String levelUpType) {
        this.levelUpType = levelUpType;
    }

    public int getSkillSlot() {
        return skillSlot;
    }

    public void setSkillSlot(int skillSlot) {
        this.skillSlot = skillSlot;
    }

    public String getWardType() {
        return wardType;
    }

    public void setWardType(String wardType) {
        this.wardType = wardType;
    }
}



