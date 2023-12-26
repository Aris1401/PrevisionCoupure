package com.panneau.coupure.service;

import com.panneau.coupure.model.Batterie;
import com.panneau.coupure.repository.BatterieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BatterieService {
    @Autowired
    private BatterieRepository batterieRepository;

    public Iterable<Batterie> getAllBatteries() {
        return batterieRepository.findAll();
    }

    public Optional<Batterie> getBatterieById(int idBatterie) {
        return batterieRepository.findById(Long.valueOf(idBatterie));
    }

    public Batterie saveBatterie(Batterie batterie) {
        return batterieRepository.save(batterie);
    }

    public double consumeBatterie(Batterie batterie, double consommation) {
        double consomationLimit = 50.0; // TODO: Changer par valeur en configuration
        double consommationLimit = ( batterie.getCapaciteMaximale() * consomationLimit ) / 100.0;

        return consommation - consommationLimit;
    }
}
