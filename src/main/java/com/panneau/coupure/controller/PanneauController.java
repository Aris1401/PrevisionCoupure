package com.panneau.coupure.controller;

import com.panneau.coupure.model.Panneau;
import com.panneau.coupure.repository.PanneauRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/panneaux")
public class PanneauController {
    @Autowired
    private PanneauRepository panneauRepository;

    @GetMapping("")
    public String index(Model model) {
        Iterable<Panneau> panneaux = panneauRepository.findAll();
        model.addAttribute("panneaux", panneaux);

        return "panneaux/index";
    }

    @PostMapping("")
    public String createPanneau(@RequestParam("capaciteMaximale") double capaciteMaximale) {
        Panneau panneau = new Panneau();
        panneau.setCapaciteMaximale(capaciteMaximale);

        panneauRepository.save(panneau);

        return "redirect:/panneaux";
    }
}
