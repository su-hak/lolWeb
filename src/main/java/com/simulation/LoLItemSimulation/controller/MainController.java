package com.simulation.LoLItemSimulation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/simulation")
    public String simulationPage(Model model){
        return "simulation";
    }

        @GetMapping("/lol")
    public String listMain(Model model){
        return "lolMain";
    }

    
    @GetMapping("/")
    public String helloPage() {
        return "index";
    }

    @GetMapping("/itemTier")
    public String itemTierList() {
        return "itemTier";
    }

    @GetMapping("/probuild")
    public String proBuild() {
        return "probuild";
    }
}
