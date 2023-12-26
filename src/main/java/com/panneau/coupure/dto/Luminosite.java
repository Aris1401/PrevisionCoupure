package com.panneau.coupure.dto;

public class Luminosite {
    double niveauLuminosite = 0;
    public Luminosite(double niveauLuminosite) {
        setNiveauLuminosite(niveauLuminosite);
    }

    public double getNiveauLuminosite() {
        return niveauLuminosite;
    }

    public void setNiveauLuminosite(double niveauLuminosite) {
        if (niveauLuminosite < 0) niveauLuminosite = 0;
        if (niveauLuminosite > 10) niveauLuminosite = 10; // TODO: Obtenir depuis configurations
        this.niveauLuminosite = niveauLuminosite;
    }
}
