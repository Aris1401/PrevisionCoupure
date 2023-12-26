package com.panneau.coupure.model;

import jakarta.persistence.*;

@Entity
public class SourceSolaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @ManyToOne
    @JoinColumn(name = "panneau")
    Panneau panneau;
    @ManyToOne
    @JoinColumn(name = "batterie")
    Batterie batterie;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Panneau getPanneau() {
        return panneau;
    }

    public void setPanneau(Panneau panneau) {
        this.panneau = panneau;
    }

    public Batterie getBatterie() {
        return batterie;
    }

    public void setBatterie(Batterie batterie) {
        this.batterie = batterie;
    }
}
