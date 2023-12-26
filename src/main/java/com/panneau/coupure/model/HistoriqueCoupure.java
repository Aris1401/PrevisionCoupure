package com.panneau.coupure.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class HistoriqueCoupure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Timestamp dateEnregistrement;
    Timestamp dateCoupure;
    @ManyToOne
    @JoinColumn(name = "secteur")
    Secteur secteur;
    String luminosite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateEnregistrement() {
        return dateEnregistrement;
    }

    public void setDateEnregistrement(Timestamp dateEnregistrement) {
        this.dateEnregistrement = dateEnregistrement;
    }

    public Timestamp getDateCoupure() {
        return dateCoupure;
    }

    public void setDateCoupure(Timestamp dateCoupure) {
        this.dateCoupure = dateCoupure;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public String getLuminosite() {
        return luminosite;
    }

    public void setLuminosite(String luminosite) {
        this.luminosite = luminosite;
    }
}
