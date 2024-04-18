package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Simulation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SimulationRepository extends JpaRepository<Simulation, Long> {

    Simulation findByPostId(Long postId);
}
