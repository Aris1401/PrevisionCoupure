package com.panneau.coupure.controller;

import com.panneau.coupure.dto.Prevision;
import com.panneau.coupure.model.HistoriqueCoupure;
import com.panneau.coupure.model.Presence;
import com.panneau.coupure.repository.HistoriqueCoupureRepository;
import com.panneau.coupure.service.HistoriqueCoupureService;
import com.panneau.coupure.service.PrevisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;

@RestController
public class TestController {
    @Autowired
    private PrevisionService previsionService;
    @Autowired
    private HistoriqueCoupureRepository historiqueCoupureRepository;
    @Autowired
    private HistoriqueCoupureService historiqueCoupureService;

    @GetMapping("/test/prevision")
    public Prevision testPrevision() {
        String luminosite = "[9,9,9,9,8,8,8,9]";
        Prevision prevision = previsionService.prevoirPourDate(Timestamp.valueOf("2024-01-03 08:00:00"), luminosite);
        System.out.println("\n\n\nHeure de coupure: " + prevision.getDateCoupure());
        return prevision;
    }

    @GetMapping("/test/prevision2")
    public Prevision testPrevision2() {
        String luminosite = "[8,7,9,9,8,7,6,4]";

        Presence presence1 = new Presence(300);
        Presence presence2 = new Presence(280);
        Presence[] presences = new Presence[] {presence1, presence2};

        return previsionService.getHeureDeCoupurePour(Timestamp.valueOf("2023-12-13 08:00:00"), 80, luminosite, presences);
    }

    @GetMapping("/test/prevision3")
    public double testPrevision3() {
        Iterable<HistoriqueCoupure> historiqueCoupures = historiqueCoupureRepository.findAll();
        HistoriqueCoupure h = null;

        for (HistoriqueCoupure historiqueCoupure : historiqueCoupures) {
            h = historiqueCoupure;
            break;
        }

        return historiqueCoupureService.getConsommationPourHistorique(h);
    }

    @GetMapping("/test/prevision4")
    public double testPrevision4() {
        return historiqueCoupureService.getConsommationMoyenne(Timestamp.valueOf("2023-12-13 08:00:00"));
    }
}
