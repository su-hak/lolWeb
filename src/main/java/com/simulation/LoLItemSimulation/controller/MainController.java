package com.simulation.LoLItemSimulation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/simulation")
    public String list(Model model){
        return "simulation";
    }

    @GetMapping("/")
    public String helloPage() {
        return "index";
    }
}
