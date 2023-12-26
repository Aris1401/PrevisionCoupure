package com.panneau.coupure.model;

import jakarta.persistence.*;

@Entity
public class SourceSolaireSecteur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "secteur")
    Secteur secteur;
    @ManyToOne
    @JoinColumn(name = "source_solaire")
    SourceSolaire sourceSolaire;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public SourceSolaire getSourceSolaire() {
        return sourceSolaire;
    }

    public void setSourceSolaire(SourceSolaire sourceSolaire) {
        this.sourceSolaire = sourceSolaire;
    }
}
