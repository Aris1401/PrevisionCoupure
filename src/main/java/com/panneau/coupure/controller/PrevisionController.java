package com.panneau.coupure.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@RequestMapping("/prevision")
public class PrevisionController {
    Date datePrevision;

    @GetMapping("")
    public String index(Model model) {
        return "prevision/index";
    }

    @PostMapping("/meteo")
    public String prevoirSurDate(@RequestParam("date-prevision") Date datePrevision, Model model) {
        this.datePrevision = datePrevision;

        model.addAttribute("date-prevision", datePrevision);
        return "prevision/meteo";
    }
}
