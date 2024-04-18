package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.domain.Simulation;
import com.simulation.LoLItemSimulation.repository.SimulationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimulationService {
    private final SimulationRepository simulationRepository;

    @Autowired
    public SimulationService(SimulationRepository simulationRepository) {
        this.simulationRepository = simulationRepository;
    }

    public void submitSimulation(Long post, String nextATagClass1, String nextATagClass2, String nextATagClass3,
                                 String nextATagClass4, String nextATagClass5, String nextATagClass6,
                                 String nextATagClass7) {
        Simulation simulation = new Simulation();
        simulation.setPostId(post);
        simulation.setChampionName(nextATagClass1);
        simulation.setItem1(nextATagClass2);
        simulation.setItem2(nextATagClass3);
        simulation.setItem3(nextATagClass4);
        simulation.setItem4(nextATagClass5);
        simulation.setItem5(nextATagClass6);
        simulation.setItem6(nextATagClass7);

        simulationRepository.save(simulation);
    }

}