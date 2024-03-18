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
        String tier = "DIAMOND";
        String division = "I";

        // ProbuildService의 getLeagueInfo 메서드를 호출하여 해당 큐, 티어, 디비전에 속한 모든 정보를 가져옵니다.
        /*LeagueEntryDTO[] leagueEntries = probuildService.getLeagueInfo(queue, tier, division);*/
        List<LeagueEntryDTO> leagueEntries = new ArrayList<>(); // ArrayList 생성
        /*leagueEntries.add(new LeagueEntryDTO());*/
        for (LeagueEntryDTO entry : probuildService.getLeagueInfo(queue, tier, division)) {
            leagueEntries.add(entry);
        }

        // 가져온 정보를 모델에 추가하여 view로 전달합니다.
        model.addAttribute("leagueEntries", leagueEntries);

        // 해당 정보를 보여줄 view의 이름을 반환합니다
        return "probuild";
    }
}
