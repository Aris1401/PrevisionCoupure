package com.panneau.coupure.service;

import com.panneau.coupure.model.Salle;
import com.panneau.coupure.model.Secteur;
import com.panneau.coupure.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecteurService {
    @Autowired
    private SalleRepository salleRepository;

    public Iterable<Salle> obtenirSalleBySecteur(Secteur secteur) {
        return salleRepository.findBySecteur(secteur);
    }
}
