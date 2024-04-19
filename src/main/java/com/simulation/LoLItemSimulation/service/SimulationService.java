package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import com.simulation.LoLItemSimulation.domain.Simulation;
import com.simulation.LoLItemSimulation.dto.PostDto;
import com.simulation.LoLItemSimulation.dto.SimulationDTO;
import com.simulation.LoLItemSimulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SimulationService {
    private final SimulationRepository simulationRepository;

    @Autowired
    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public void submitSimulation(Long postId, String nextATagClass1, String nextATagClass2, String nextATagClass3,
                                 String nextATagClass4, String nextATagClass5, String nextATagClass6,
                                 String nextATagClass7) {
        Simulation simulation = new Simulation();
        simulation.setPostId(postId);
        simulation.setChampionName(nextATagClass1);
//        simulation.setItem1(nextATagClass2);

        if (nextATagClass2 == null || nextATagClass2.isEmpty()) {
            simulation.setItem1(null);
        } else {
            simulation.setItem1(nextATagClass2);
        }

        if (nextATagClass3 == null || nextATagClass3.isEmpty()) {
            simulation.setItem2(null);
        } else {
            simulation.setItem2(nextATagClass3);
        }

        if (nextATagClass4 == null || nextATagClass4.isEmpty()) {
            simulation.setItem3(null);
        } else {
            simulation.setItem3(nextATagClass4);
        }

        if (nextATagClass5 == null || nextATagClass5.isEmpty()) {
            simulation.setItem4(null);
        } else {
            simulation.setItem4(nextATagClass5);
        }

        if (nextATagClass6 == null || nextATagClass6.isEmpty()) {
            simulation.setItem5(null);
        } else {
            simulation.setItem5(nextATagClass6);
        }

        if (nextATagClass7 == null || nextATagClass7.isEmpty()) {
            simulation.setItem6(null);
        } else {
            simulation.setItem6(nextATagClass7);
        }

        simulationRepository.save(simulation);
    }

    public SimulationDTO getSimulationDtoById(Long postId) {
        // postId에 해당하는 게시글 정보를 불러옴
        Simulation simulation = simulationRepository.findByPostId(postId);

        if (simulation == null) {
            // 게시글이 없을 경우 예외처리
            return null;
        }

        // Post 엔터티를 PostDto로 변환
        return convertSimulEntityToDto(simulation);
    }

    public SimulationDTO convertSimulEntityToDto(Simulation simulation) {
        SimulationDTO simulationDTO = new SimulationDTO();
        simulationDTO.setId(simulation.getId());
        simulationDTO.setPostId(simulation.getPostId());
        simulationDTO.setChampionName(simulation.getChampionName());
        simulationDTO.setItem1(simulation.getItem1());
        simulationDTO.setItem2(simulation.getItem2());
        simulationDTO.setItem3(simulation.getItem3());
        simulationDTO.setItem4(simulation.getItem4());
        simulationDTO.setItem5(simulation.getItem5());
        simulationDTO.setItem6(simulation.getItem6());

        String championImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + simulation.getChampionName() + ".png";
        simulationDTO.setChampionImageUrl(championImageUrl);

        if(simulation.getItem1()!=null){
            String item1ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem1();
            simulationDTO.setItem1ImageUrl(item1ImageUrl);
        }else{
            simulationDTO.setItem1ImageUrl("null");
        }
        if(simulation.getItem2()!=null) {
            String item2ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem2();
            simulationDTO.setItem2ImageUrl(item2ImageUrl);
        }else{
            simulationDTO.setItem2ImageUrl("null");
        }

        if(simulation.getItem3()!=null) {
            String item3ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem3();
            simulationDTO.setItem3ImageUrl(item3ImageUrl);
        }else{
            simulationDTO.setItem3ImageUrl("null");
        }

        if(simulation.getItem4()!=null) {
            String item4ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem4();
            simulationDTO.setItem4ImageUrl(item4ImageUrl);
        }else{
            simulationDTO.setItem4ImageUrl("null");
        }

        if(simulation.getItem5()!=null) {
            String item5ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem5();
            simulationDTO.setItem5ImageUrl(item5ImageUrl);
        }else{
            simulationDTO.setItem5ImageUrl("null");
        }

        if(simulation.getItem6()!=null) {
            String item6ImageUrl = "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + simulation.getItem6();
            simulationDTO.setItem6ImageUrl(item6ImageUrl);
        }else{
            simulationDTO.setItem6ImageUrl("null");
        }

        return simulationDTO;
    }

    public String generateChampionImageUrl(String championName) {
//        System.out.println("generateChampionImageUrl:::"+championName);
        if (championName != "null") {
            return "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/champion/" + championName + ".png";
        } else {
            return null;
        }
    }

    public String generateItemImageUrl(String item) {
        if (item != "null") {
            return "https://ddragon.leagueoflegends.com/cdn/14.6.1/img/item/" + item;
        } else {
            return null;
        }
    }


}