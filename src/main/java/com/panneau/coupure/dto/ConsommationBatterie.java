package com.panneau.coupure.dto;

import com.panneau.coupure.model.Batterie;
import com.panneau.coupure.model.Secteur;

public class ConsommationBatterie {
    Secteur secteur;
    Batterie batterie;
    double consommation;
    double reste = 0;
    double heure;
    double recharge = 0;

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Batterie getBatterie() {
        return batterie;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }

    public double getConsommation() {
        return consommation;
    }

    public void setConsommation(double consommation) {
        if (consommation < 0) consommation = 0;
        this.consommation = consommation;
    }

    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }

    public double getHeure() {
        return heure;
    }

    public void setHeure(double heure) {
        this.heure = heure;
    }

    public double getRecharge() {
        return recharge;
    }

    public void setRecharge(double recharge) {
        this.recharge = recharge;
    }
}
