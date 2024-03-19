// ProbuildService.java
package com.simulation.LoLItemSimulation.service;

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
    private final String apiKey = "RGAPI-bede6e6e-c776-4560-8026-44b4d52113c1"; // 여기에 실제 API 키를 넣습니다.
    private RestTemplate restTemplate;
    private List<List<String>> matchIdsByPuuid = new ArrayList<>();

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
            // puuid 설정
            for (LeagueEntryDTO entry : leagueEntries) {
                setPuuidBySummonerName(entry);
                setMatchIdsByPuuid(entry);
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
            entry.setMatchIds(Arrays.asList(matchIds));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


/*    public List<String> getMatchIdsByPuuid(String puuid) {
        if (puuid == null) {
            return Collections.emptyList(); // 또는 적절한 처리 방법 선택
        }
        String url = "https://asia.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=0&count=1&api_key=" + apiKey;
        try {
            String[] matchIds = restTemplate.getForObject(url, String[].class);
            return Arrays.asList(matchIds);
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList(); // 또는 적절한 처리 방법 선택
        }
    }*/

}

