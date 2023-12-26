package com.panneau.coupure.dto;

import com.panneau.coupure.model.Presence;
import com.panneau.coupure.model.Secteur;
import com.panneau.coupure.model.SourceSolaire;

import java.util.ArrayList;

public class DetailsCoupure {
    /**
     * Secteur a laquelle la coupure va se faire;
     */
    Secteur secteur;
    double consommationMoyenneEtudiant;
    Presence[] presences;

    public DetailsCoupure() {

    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public double getConsommationMoyenneEtudiant() {
        return consommationMoyenneEtudiant;
    }

    public void setConsommationMoyenneEtudiant(double consommationMoyenneEtudiant) {
        if (consommationMoyenneEtudiant < 0) consommationMoyenneEtudiant = 0;

        this.consommationMoyenneEtudiant = consommationMoyenneEtudiant;
    }

    public Presence[] getPresences() {
        return presences;
    }

    public void setPresences(Presence[] presences) {
        this.presences = presences;
    }
}
