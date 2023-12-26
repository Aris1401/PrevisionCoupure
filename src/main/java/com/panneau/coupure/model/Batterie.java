package com.panneau.coupure.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Batterie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    double capaciteMaximale;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCapaciteMaximale() {
        return capaciteMaximale;
    }

    public void setCapaciteMaximale(double capaciteMaximale) {
        this.capaciteMaximale = capaciteMaximale;
    }

    @Override
    public boolean equals(Object obj) {
        Batterie otherBatterie = (Batterie) obj;
        return otherBatterie.getId() == getId();
    }
}
