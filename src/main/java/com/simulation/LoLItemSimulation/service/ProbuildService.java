package com.simulation.LoLItemSimulation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.simulation.LoLItemSimulation.dto.LeagueEntryDTO;

@Service
public class ProbuildService {

    private final String apiKey = "RGAPI-65c84dd7-bab1-4938-a91b-e090fe14cbc3"; // 여기에 실제 API 키를 넣습니다.

    public LeagueEntryDTO getLeagueInfo(String summonerId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://[region].api.riotgames.comhttps://developer.riotgames.com/apis#league-exp-v4/GET_getLeagueEntries" + summonerId + "?api_key=" + apiKey;

        LeagueEntryDTO[] response = restTemplate.getForObject(url, LeagueEntryDTO[].class);
        if (response != null && response.length > 0) {
            return response[0]; // 예제를 단순화하기 위해 첫 번째 결과만 반환합니다.
        }
        return null;
    }
}
