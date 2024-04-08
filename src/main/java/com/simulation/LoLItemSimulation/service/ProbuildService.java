// ProbuildService.java
package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.dto.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;


@Service
public class ProbuildService {
    private final String apiKey = "";
    private RestTemplate restTemplate;

    public ProbuildService(RestTemplateBuilder restTemplateBuilder) { // 생성자에서 RestTemplate 초기화
        this.restTemplate = restTemplateBuilder.build();
    }

    // Page<leagueEntryDTO> 페이징 처리할려면 이 코드로 변경
    public LeagueEntryDTO[] getLeagueInfo(String queue, String tier, String division/*, int limit, int page*/) {
        /*int offset = (page - 1) * limit;*/

        RestTemplate restTemplate = new RestTemplate();
        System.out.println("qu : " + queue + "  tier : " + tier + " division  : " + division);
        String url = "https://kr.api.riotgames.com/lol/league-exp/v4/entries/" + queue + "/" + tier + "/" + division + "?api_key=" + apiKey /*+ "&start=" + offset + "&limit=" + limit*/;
        System.out.println(" url ::" + url);

        try {
            LeagueEntryDTO[] leagueEntries = restTemplate.getForObject(url, LeagueEntryDTO[].class);
            /*// puuid 설정 (전체)
            for (LeagueEntryDTO entry : leagueEntries) {
                setPuuidBySummonerName(entry);
                setMatchIdsByPuuid(entry);
                setProbuild(entry);
            }*/

            // 3개
            List<LeagueEntryDTO> limitedEntries = new ArrayList<>();
            for (int i = 0; i < Math.min(3, leagueEntries.length); i++) {
                limitedEntries.add(leagueEntries[i]);
            }

            int totalCount = leagueEntries.length;

            // puuid 설정 // entry : limitedEntries로 바꿔야 3개의 forEach문 변수가 적용 됨.
            for (LeagueEntryDTO entry : limitedEntries) {
                setPuuidBySummonerName(entry);
                setChampionPoints(entry);
                setMatchIdsByPuuid(entry);
                setProbuild(entry);
                setProbuildTimeline(entry);
            }
            /*return new PageImpl<>(Arrays.asList(leagueEntries), PageRequest.of(page, limit), totalCount);*/
            return leagueEntries;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 소환사 이름을 기반으로 puuid를 가져오는 메서드
    private void setPuuidBySummonerName(LeagueEntryDTO entry) {
        RestTemplate restTemplate = new RestTemplate();
        String summonerName = entry.getSummonerName();
        String url = "https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + summonerName + "?api_key=" + apiKey;
        try {
            SummonerDTO summonerDTO = restTemplate.getForObject(url, SummonerDTO.class);
            entry.setPuuid(summonerDTO.getPuuid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("summonerName ::" + url);
    }

    private void setChampionPoints(LeagueEntryDTO entryDTO) {
        RestTemplate restTemplate = new RestTemplate();
        String puuid = entryDTO.getPuuid();
        String url = "https://kr.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/" + puuid + "?api_key=" + apiKey;

        try {
            ChampionMasteryDTO[] championMastery = restTemplate.getForObject(url, ChampionMasteryDTO[].class);
            Arrays.sort(championMastery, Comparator.comparingInt(ChampionMasteryDTO::getChampionPoints).reversed());

            List<Integer> topoChampionIds = new ArrayList<>();
            int count = 0;
            for (ChampionMasteryDTO masteryDTO : championMastery) {
                topoChampionIds.add(masteryDTO.getChampionId());
                count++;
                if (count == 3) {
                    break;
                }
            }
            entryDTO.setChampionId(topoChampionIds);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("championId ::" + url);
    }

    // matchIds 가져오기
    private void setMatchIdsByPuuid(LeagueEntryDTO entry) {
        RestTemplate restTemplate1 = new RestTemplate();
        String puuid = entry.getPuuid();
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=1&api_key=" + apiKey;
        try {
            String[] matchIds = restTemplate1.getForObject(url, String[].class);
            // matchIds가 null이 아닌 경우에만 설정
            if (matchIds != null) {
                entry.setMatchIds(Arrays.asList(matchIds));
            } else {
                System.out.println("MatchIds is null. Skipping setting matchIds.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("puuid ::" + url);
    }

    // matchId로 매칭 게임 정보 가져오기
    private void setProbuild(LeagueEntryDTO pro) {
        // matchIds가 null이 아니고 비어 있지 않은 경우에만 api 호출
        if (pro.getMatchIds() != null && !pro.getMatchIds().isEmpty()) {
            RestTemplate restTemplate1 = new RestTemplate();
            List<String> matchIdsList = pro.getMatchIds();

            // 각 요소에서 대괄호를 제거하고 안에 있는 문자열만 추출하여 matchIds로 저장
            String matchIds = matchIdsList.get(0).replaceAll("\\[|\\]", ""); // 대괄호 제거

            // matchIds가 빈 문자열이 아닌 경우에만 API 호출
            if (!matchIds.isEmpty()) {
                String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchIds + "?api_key=" + apiKey;

                try {
                    MatchDetailDTO matchDetail = restTemplate1.getForObject(url, MatchDetailDTO.class);

                    if (matchDetail != null && matchDetail.getInfo() != null && matchDetail.getInfo().getParticipants() != null) {
                        // 플레이어들의 정보를 LeagueEntryDTO 객체에 추가
                        List<MatchDetailDTO.Participant> participants = matchDetail.getInfo().getParticipants();
                        for (MatchDetailDTO.Participant participant : participants) {
                            String summonerName = participant.getSummonerName();
                            // 만약 summonerName이 빈 문자열이면 "닉네임 알 수 없음"으로 설정
                            if (summonerName.isEmpty()) {
                                participant.setSummonerName("\"알 수 없는 소환사\"");
                            }
                        }
                        pro.setParticipants(participants);
                    }
                    System.out.println("Match url ::" + url);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("matchIds is empty. Skipping API call.");
            }
        } else {
            System.out.println("matchIds is null or empty. Skipping API call.");
        }
    }


    // 인게임 Timeline 가져오기
    private void setProbuildTimeline(LeagueEntryDTO entry) {
        RestTemplate restTemplate = new RestTemplate();

        List<String> matchIdsList = entry.getMatchIds();

        // 각 요소에서 대괄호를 제거하고 안에 있는 문자열만 추출하여 matchIds로 저장
        String matchIds = matchIdsList.get(0).replaceAll("\\[|\\]", ""); // 대괄호 제거

        // matchIds가 빈 문자열이 아닌 경우에만 API 호출
        if (!matchIds.isEmpty()) {
            String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchIds + "/timeline?api_key=" + apiKey;
            try {
                MatchTimelineDTO matchTimeline = restTemplate.getForObject(url, MatchTimelineDTO.class);

                // 매치에 참가한 모든 플레이어의 정보를 가져옴
                List<MatchTimelineDTO.Participant> participants = matchTimeline.getInfo().getParticipants();
                List<MatchTimelineDTO.Frame> frames = matchTimeline.getInfo().getFrames();

                // url의 puuid와 같은 배열에 있는 participantId를 가져오기 위해 LeagueEntryDTO에 저장된 puuid를 사용
                String entryPuuid = entry.getPuuid(); // LeagueEntryDTO에 저장된 puuid 가져오기
                for (MatchTimelineDTO.Participant participant : participants) {
                    // LeagueEntryDTO에 저장된 puuid와 url의 puuid를 비교
                    if (participant.getPuuid().equals(entryPuuid)) {
                        // puuid가 일치하는 경우 해당하는 participantId를 출력
                       entry.setParticipantId(participant.getParticipantId());

                        int targetId = entry.getParticipantId();
                        if (participant.getParticipantId() == targetId) {
                            // 해당 Participant의 모든 항목들을 출력
                            if (frames != null) {
                                int count = 0;

                                for (MatchTimelineDTO.Frame frame : frames) {
                                    List<MatchTimelineDTO.Event> events = frame.getEvents();

                                    // 프레임
                                    entry.setFrame(frames);
                                    // 총 게임시간 계산 조건문
                                    if (frames != null && !frames.isEmpty()) {
                                        MatchTimelineDTO.Frame lastFrame = frames.get(frames.size() - 1);
                                        int lastTimestamp = lastFrame.getTimestamp();

                                        entry.setLastTimestamp(lastTimestamp);
                                        } else if (events != null) {
                                        System.out.println("frames is null");
                                    }


                                    /*removeItem(events, frames, entry);*/

                                    // 이벤트
                                    if (events != null) {
                                        for (MatchTimelineDTO.Event event : events) {
                                            if (event.getParticipantId() == targetId) {

                                                entry.setTimestamp(event.getTimestamp());
                                                entry.setType(event.getType());
                                                entry.setEvent(events);

                                                // 제어 와드 총 구매 갯수
                                                if (entry.getItemId() == 2055
                                                && entry.getType().equals("ITEM_PURCHASED")) {
                                                    count++;
                                                }


//                                                removeEventsItem(events, entry);



                                                entry.setItemId(event.getItemId());
                                            }
                                        }
                                }

                                removeFramesItem(events, frame, entry);
                            }
                                entry.setControlWard(count);

                            } else {
                            System.out.println("frames is null");
                        }
                    }
                }
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Timeline ::" + url);
    }
}

    private static void removeEventsItem(List<MatchTimelineDTO.Event> events, LeagueEntryDTO entry) {

        int targetId = entry.getParticipantId();
        for (MatchTimelineDTO.Event event : events) {
            if (event.getParticipantId() == targetId) {

                /*System.out.println("eventId" + event);*/

                int beforeId = event.getBeforeId();
                int eventSize = events.size();

                for (int i = eventSize - 1; i >= 0; i--) {
                    MatchTimelineDTO.Event prevEvent = events.get(i);
                    if (prevEvent.getItemId() == beforeId
                            && prevEvent.getItemId() != 0
                            && beforeId != 0
                            && prevEvent.getItemId() != 2055
                            && beforeId != 2055) {
                        events.remove(i);
                        entry.setItemId(prevEvent.getItemId());
                        break;
                    }
                }
            }
        }
    }

    private static void removeFramesItem(List<MatchTimelineDTO.Event> events, MatchTimelineDTO.Frame frame, LeagueEntryDTO entry) {

        int targetId = entry.getParticipantId();
        List<MatchTimelineDTO.Event> removed = new ArrayList();
        for (MatchTimelineDTO.Event event : events) {
            if (event.getParticipantId() == targetId) {
                if(event.getBeforeId() != 0 && event.getBeforeId() != 2055){
                    int beforeId = event.getBeforeId();
                    System.out.println("비포아이디 : " + beforeId);
                    int timeStamp = event.getTimestamp(); // before의 timestamp

                    removed.add(event);
                    //events.remove(events.indexOf(event));

                    for(MatchTimelineDTO.Event inevent : events){
//                        if(inevent.getItemId() == beforeId ){
                        if(inevent.getItemId() == beforeId && inevent.getTimestamp() <= timeStamp ){
                            System.out.println(inevent.getItemId() + " ::: " + event.getBeforeId());
                            System.out.println(inevent.getTimestamp() + " ::: " + event.getTimestamp());
                            //events.remove(events.indexOf(inevent));
                            removed.add(inevent);

                        }
                    }

                }
            }
        }
        events.removeAll(removed);
    }
}

