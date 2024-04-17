// LeagueEntryDTO.java
package com.simulation.LoLItemSimulation.dto;




import java.util.List;

public class LeagueEntryDTO {
    private String leagueId;
    private String summonerId;
    private String summonerName;
    private String riotIdGameName;
    private String riotIdTagline;
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
    private List<ParticipantDTO> participants;

    public List<ParticipantDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<ParticipantDTO> participants) {
        this.participants = participants;
    }

    private String championName;
    private int kills;
    private int deaths;
    private int assists;
    private List<Integer> championId;
    private int championPoints;

    private int perk;
    private int style;
    private int defense;
    private int flex;
    private int offense;
    private List<PerkStatsDTO> statPerks;
    private List<PerkStyleDTO> styles;
    private List<PerkStyleSelectionDTO> selections;

    public int getPerk() {
        return perk;
    }

    public void setPerk(int perk) {
        this.perk = perk;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public int getDefense() {
        return defense;
    }

    public void setDefense(int defense) {
        this.defense = defense;
    }

    public int getFlex() {
        return flex;
    }

    public void setFlex(int flex) {
        this.flex = flex;
    }

    public int getOffense() {
        return offense;
    }

    public void setOffense(int offense) {
        this.offense = offense;
    }

    public List<PerkStatsDTO> getStatPerks() {
        return statPerks;
    }

    public List<PerkStyleDTO> getStyles() {
        return styles;
    }

    public List<PerkStyleSelectionDTO> getSelections() {
        return selections;
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

    public void setDeaths(int deaths) {
        this.deaths = deaths;
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

    public void setStatPerks(List<PerkStatsDTO> statPerks) {
        this.statPerks = statPerks;
    }

    public void setStyles(List<PerkStyleDTO> styles) {
        this.styles = styles;
    }

    public void setSelections(List<PerkStyleSelectionDTO> selections) {
        this.selections = selections;
    }

    public void setEvents(List<MatchTimelineDTO.Event> events) {
        this.events = events;
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


    // Timeline
    private List<MatchTimelineDTO.Info> info;

    public List<MatchTimelineDTO.Info> getInfo() {
        return info;
    }

    public void setInfo(List<MatchTimelineDTO.Info> info) {
        this.info = info;
    }
    private List<MatchTimelineDTO.Frame> frames;

    public List<MatchTimelineDTO.Frame> getFrame() {
        return frames;
    }

    public void setFrame(List<MatchTimelineDTO.Frame> frames) {

        this.frames = frames;
    }

    private List<MatchTimelineDTO.Event> events;

    public List<MatchTimelineDTO.Event> getEvent() {
        return events;
    }

    public void setEvent(List<MatchTimelineDTO.Event> events) {

        this.events = events;
    }

    private int participantId;

    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }
    private int itemId;
    private int beforeId;
    private int afterId;
    private int timestamp;
    private int lastTimestamp;
    private String type;
    private String levelUpType;
    private int skillSlot;
    private int controlWard;


    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBeforeId() {
        return beforeId;
    }

    public void setBeforeId(int beforeId) {
        this.beforeId = beforeId;
    }

    public int getAfterId() {
        return afterId;
    }

    public void setAfterId(int afterId) {
        this.afterId = afterId;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public int getLastTimestamp() {
        return lastTimestamp;
    }

    public void setLastTimestamp(int lastTimestamp) {
        int min = lastTimestamp / (1000 * 60);
        int sec = (lastTimestamp / 1000) % 60;
        this.lastTimestamp = lastTimestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getControlWard() {
        return controlWard;
    }

    public void setControlWard(int controlWard) {
        this.controlWard = controlWard;
    }
}



