// ProbuildService.java
package com.simulation.LoLItemSimulation.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.simulation.LoLItemSimulation.dto.LeagueEntryDTO;

@Service
public class ProbuildService {

    private final String apiKey = "RGAPI-1ea0991a-50ba-4a01-a7cd-a386faf246e4"; // 여기에 실제 API 키를 넣습니다.

    public LeagueEntryDTO[] getLeagueInfo(String queue, String tier, String division) {
        RestTemplate restTemplate = new RestTemplate();
        System.out.println("qu : " + queue + "  tier : " + tier  + " division  : " +  division) ;
        String url = "https://kr.api.riotgames.com/lol/league-exp/v4/entries/"+queue+"/"+ tier + "/" + division+ "?api_key=" + apiKey;
        System.out.println(" url ::" +url);
        return restTemplate.getForObject(url, LeagueEntryDTO[].class, queue, tier, division);
    }
}
