package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.dto.LeagueEntryDTO;
import com.simulation.LoLItemSimulation.service.ProbuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProbuildController {

    private ProbuildService probuildService;

    @Autowired
    public ProbuildController(ProbuildService probuildService) {
        this.probuildService = probuildService;
    }

    @GetMapping("/probuild")
    public String getLeagueInfo(Model model) {
        // 호출할 때 사용할 큐, 티어, 디비전 정보를 지정합니다
        String queue = "RANKED_SOLO_5x5";
        String tier = "CHALLENGER";
        String division = "I";

        // ProbuildService의 getLeagueInfo 메서드를 호출하여 해당 큐, 티어, 디비전에 속한 모든 정보를 가져옵니다.
        List<LeagueEntryDTO> leagueEntries = new ArrayList<>(); // ArrayList 생성

        // ProbuildService에서 최대 3개의 배열만 가져오도록 수정
        LeagueEntryDTO[] entries = probuildService.getLeagueInfo(queue, tier, division);
        if (entries != null) {
            for (int i = 0; i < Math.min(3, entries.length); i++) {
                leagueEntries.add(entries[i]);
            }
        } else {
            // 요청이 실패한 경우에 대한 처리를 여기에 추가합니다.
            // 예: 로깅, 오류 메시지 등
        }

        /* // 전체 배열 출력
        for (LeagueEntryDTO entry : probuildService.getLeagueInfo(queue, tier, division)) {
            leagueEntries.add(entry);
            System.out.println(leagueEntries + "league fucking entries");
        }*/


        // 가져온 정보를 모델에 추가하여 view로 전달합니다.
        model.addAttribute("leagueEntries", leagueEntries);

        // 해당 정보를 보여줄 view의 이름을 반환합니다
        return "probuild";
    }

}
