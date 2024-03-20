// ProbuildService.java
package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.dto.MatchDetailDTO;
import com.simulation.LoLItemSimulation.dto.ParticipantDTO;
import com.simulation.LoLItemSimulation.dto.SummonerDTO;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import com.simulation.LoLItemSimulation.dto.LeagueEntryDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Service
public class ProbuildService {
    private final String apiKey = "RGAPI-8bf9a6e0-8044-454c-b456-0e8bfa615cb3"; // 여기에 실제 API 키를 넣습니다.
    private RestTemplate restTemplate;

    public ProbuildService(RestTemplateBuilder restTemplateBuilder) { // 생성자에서 RestTemplate 초기화
        this.restTemplate = restTemplateBuilder.build();
    }

    public LeagueEntryDTO[] getLeagueInfo(String queue, String tier, String division) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("qu : " + queue + "  tier : " + tier + " division  : " + division);
        String url = "https://kr.api.riotgames.com/lol/league-exp/v4/entries/" + queue + "/" + tier + "/" + division + "?api_key=" + apiKey;
        System.out.println(" url ::" + url);

        try {
            LeagueEntryDTO[] leagueEntries = restTemplate.getForObject(url, LeagueEntryDTO[].class);
            // puuid 설정 (전체)
            /*for (LeagueEntryDTO entry : leagueEntries) {
                setPuuidBySummonerName(entry);
                setMatchIdsByPuuid(entry);
                setProbuild(entry);
            }*/
            // 3개
            List<LeagueEntryDTO> limitedEntries = new ArrayList<>();
            for (int i = 0; i < Math.min(3, leagueEntries.length); i++) {
                limitedEntries.add(leagueEntries[i]);
            }

            // puuid 설정
            for (LeagueEntryDTO entry : limitedEntries) {
                setPuuidBySummonerName(entry);
                setMatchIdsByPuuid(entry);
                setProbuild(entry);
            }
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
    }


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
    }
    /*private void setProbuild(LeagueEntryDTO pro) {
        // matchIds가 null이 아니고 비어 있지 않은 경우에만 api 호출
        if (pro.getMatchIds() != null && !pro.getMatchIds().isEmpty()) {
            RestTemplate restTemplate1 = new RestTemplate();
            List<String> matchIdsList = pro.getMatchIds();
            System.out.println(matchIdsList + "matchIdsList");

            // 각 요소에서 대괄호를 제거하고 안에 있는 문자열만 추출하여 matchIds로 저장
            String matchIds = matchIdsList.get(0).replaceAll("\\[|\\]", ""); // 대괄호 제거
            System.out.println(matchIds + "matchIds");

            // matchIds가 빈 문자열이 아닌 경우에만 API 호출
            if (!matchIds.isEmpty()) {
                String url = "https://asia.api.riotgames.com/lol/match/v5/matches/" + matchIds + "?api_key=" + apiKey;
                System.out.println("matchIds url :" + url);
                try {
                    MatchDetailDTO matchDetail = restTemplate1.getForObject(url, MatchDetailDTO.class);
                    if (matchDetail != null && matchDetail.getInfo() != null && matchDetail.getInfo().getParticipants() != null) {
                        // 킬 수, 죽음 수, 아이템 정보를 LeagueEntryDTO 객체에 저장
                        List<MatchDetailDTO.Participant> participants = matchDetail.getInfo().getParticipants();
                        for (MatchDetailDTO.Participant participant : participants) {
                            if (participant.getSummonerName().equals(pro.getSummonerName())) {
                                pro.setSummonerName(participant.getSummonerName());
                                pro.setChampionName(participant.getChampionName());
                                pro.setSummoner1Id(participant.getSummoner1Id());
                                pro.setSummoner2Id(participant.getSummoner2Id());
                                pro.setKills(participant.getKills());
                                pro.setDeaths(participant.getDeaths());
                                pro.setAssists(participant.getAssists());
                                pro.setItem0(participant.getItem0());
                                pro.setItem0(participant.getItem1());
                                pro.setItem0(participant.getItem2());
                                pro.setItem0(participant.getItem3());
                                pro.setItem0(participant.getItem4());
                                pro.setItem0(participant.getItem5());
                                pro.setItem0(participant.getItem6());
                                pro.setTotalDamageDealt(participant.getTotalDamageDealt());
                                // 추가적인 아이템 정보를 저장할 수 있습니다.
                                break;
                            }
                        }
                    }
                    *//*if (matchDetail != null && matchDetail.getInfo() != null && matchDetail.getInfo().getParticipants() != null) {
                        for (MatchDetailDTO.Participant participant : matchDetail.getInfo().getParticipants()) {
                            String summonerName = participant.getSummonerName();
                            int kills = participant.getKills();
                            int deaths = participant.getDeaths();
                            int item0 = participant.getItem0();

                            System.out.println("Summoner Name: " + summonerName);
                            System.out.println("Kills: " + kills);
                            System.out.println("Deaths: " + deaths);
                            System.out.println("item 0: " + item0);
                        }
                    }*//*
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("matchIds is empty. Skipping API call.");
            }
        } else {
            System.out.println("matchIds is null or empty. Skipping API call.");
        }
    }*/
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
                        pro.setParticipants(matchDetail.getInfo().getParticipants());
                    }
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

}

