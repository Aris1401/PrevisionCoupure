package com.panneau.coupure.exceptions;

import com.panneau.coupure.dto.ConsommationBatterie;

public class LimiteConsommationException extends Exception {
    ConsommationBatterie consommationBatterie;
    double neededConsommation;

    public LimiteConsommationException(ConsommationBatterie consommationBatterie, double neededConsommation) {
        this.consommationBatterie = consommationBatterie;
        this.neededConsommation = neededConsommation;
    }

    public ConsommationBatterie getConsommationBatterie() {
        return consommationBatterie;
    }

    public void setConsommationBatterie(ConsommationBatterie consommationBatterie) {
        this.consommationBatterie = consommationBatterie;
    }

    public double getNeededConsommation() {
        return neededConsommation;
    }

    public void setNeededConsommation(double neededConsommation) {
        this.neededConsommation = neededConsommation;
    }
}
