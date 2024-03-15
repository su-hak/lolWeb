package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.service.ProbuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.simulation.LoLItemSimulation.service.ProbuildService;

@Controller
public class ProbuildController {

    @Autowired
    private ProbuildService probuildService;


}
