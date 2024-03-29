package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.dto.LeagueEntryDTO;
import com.simulation.LoLItemSimulation.service.ProbuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getLeagueInfo(Model model/*, @RequestParam(defaultValue = "1") int page*/) {
        // 호출할 때 사용할 큐, 티어, 디비전 정보를 지정합니다
        String queue = "RANKED_SOLO_5x5";
        String tier = "CHALLENGER";
        String division = "I";
        int pageSize = 10;

        // ProbuildService의 getLeagueInfo 메서드를 호출하여 해당 큐, 티어, 디비전에 속한 모든 정보를 가져옵니다.
        List<LeagueEntryDTO> leagueEntries = new ArrayList<>(); // ArrayList 생성

        // 최대 3번만 요청을 보냅니다.
        LeagueEntryDTO[] entries = probuildService.getLeagueInfo(queue, tier, division);
            if (entries != null) {
                for (int i = 0; i < Math.min(3, entries.length); i++) {
                    leagueEntries.add(entries[i]);
                }
            } else {
                // 요청이 실패한 경우에 대한 처리를 여기에 추가합니다.
                // 예: 로깅, 오류 메시지 등
            }

        // 가져온 정보를 모델에 추가하여 view로 전달합니다.
        model.addAttribute("leagueEntries", leagueEntries);

        // 모든 데이터를 가져옵니다.
/*        List<LeagueEntryDTO> allLeagueEntries = getAllLeagueEntries(queue, tier, division);*/

        // 현재 페이지의 데이터를 가져옵니다.
        /*List<LeagueEntryDTO> currentPageEntries = getCurrentPageEntries(allLeagueEntries, page - 1, pageSize);*/

        /*model.addAttribute("leagueEntries", currentPageEntries);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", calculateTotalPages(allLeagueEntries.size(), pageSize));*/

        // 해당 정보를 보여줄 view의 이름을 반환합니다
        return "probuild";
    }


/*    // 모든 데이터를 가져오는 메서드
    private List<LeagueEntryDTO> getAllLeagueEntries(String queue, String tier, String division) {
        // 먼저 전체 데이터를 가져옵니다.
        int limit = Integer.MAX_VALUE; // 제한 없이 모두 가져오기 위해 최대값 설정
        int page = 0; // 페이지는 0으로 설정하여 한 번에 모든 데이터를 가져옵니다.
        Page<LeagueEntryDTO> leagueEntriesPage = probuildService.getLeagueInfo(queue, tier, division, limit, page);

        // 전체 데이터를 리스트로 변환하여 반환합니다.
        return leagueEntriesPage.getContent();
    }*/

    // 현재 페이지의 데이터를 가져오는 메서드
    private List<LeagueEntryDTO> getCurrentPageEntries(List<LeagueEntryDTO> allEntries, int page, int pageSize) {
        // 현재 페이지의 시작 인덱스와 끝 인덱스를 계산합니다.
        int startIndex = page * pageSize;
        int endIndex = Math.min(startIndex + pageSize, allEntries.size());

        // 시작부터 끝까지의 데이터를 잘라서 반환합니다.
        return allEntries.subList(startIndex, endIndex);
    }

    // 전체 페이지 수를 계산하는 메서드
    private int calculateTotalPages(int totalEntries, int pageSize) {
        // 전체 데이터 수를 페이지당 데이터 수로 나눈 뒤 올림하여 반환합니다.
        return (int) Math.ceil((double) totalEntries / pageSize);
    }
}
