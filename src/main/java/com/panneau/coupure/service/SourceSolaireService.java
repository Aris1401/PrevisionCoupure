package com.panneau.coupure.service;

import com.panneau.coupure.model.Secteur;
import com.panneau.coupure.model.SourceSolaire;
import com.panneau.coupure.model.SourceSolaireSecteur;
import com.panneau.coupure.repository.SecteurRepository;
import com.panneau.coupure.repository.SourceSolaireRepository;
import com.panneau.coupure.repository.SourceSolaireSecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SourceSolaireService {
    @Autowired
    private SourceSolaireRepository sourceSolaireRepository;
    @Autowired
    private SourceSolaireSecteurRepository sourceSolaireSecteurRepository;
    @Autowired
    private SecteurRepository secteurRepository;

    public Iterable<SourceSolaire> getAllSourcesSolaires() {
        return sourceSolaireRepository.findAll();
    }

    public ArrayList<SourceSolaire> getSourceSolaireForSecteur(int idSecteur) {
        Secteur secteur = secteurRepository.findById(Long.valueOf(idSecteur)).get();
        Iterable<SourceSolaireSecteur> sourceSolaireSecteurs = sourceSolaireSecteurRepository.findBySecteur(secteur);

        ArrayList<SourceSolaire> sourceSolaires = new ArrayList<>();
        for (SourceSolaireSecteur sourceSolaireSecteur : sourceSolaireSecteurs) {
            sourceSolaires.add(sourceSolaireSecteur.getSourceSolaire());
        }

        return sourceSolaires;
    }
}
