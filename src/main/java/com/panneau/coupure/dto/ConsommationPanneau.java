package com.panneau.coupure.dto;

import com.panneau.coupure.model.Panneau;
import com.panneau.coupure.model.Secteur;

public class ConsommationPanneau {
    Secteur secteur;
    Panneau panneau;
    double consommation;
    double luminosite;
    double heure;
    double empruntBatterie;
    double puissanceAccordee;

    public double getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(double luminosite) {
        this.luminosite = luminosite;
    }

    public double getPuissanceAccordee() {
        return puissanceAccordee;
    }

    public void setPuissanceAccordee(double puissanceAccordee) {
        this.puissanceAccordee = puissanceAccordee;
    }

    public double getEmpruntBatterie() {
        return empruntBatterie;
    }

    public void setEmpruntBatterie(double empruntBatterie) {
        this.empruntBatterie = empruntBatterie;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Panneau getPanneau() {
        return panneau;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        this.consommation = consommation;
    }

    public double getHeure() {
        return heure;
    }

    public void setHeure(double heure) {
        this.heure = heure;
    }
}
