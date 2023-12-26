package com.panneau.coupure.model;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
public class Presence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    Timestamp datePresence;
    double nombrePersonne;

    @ManyToOne
    @JoinColumn(name = "salle")
    Salle salle;

    public Presence() {

    }

    public Presence(double nombrePersonne) {
        setNombrePersonne(nombrePersonne);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDatePresence() {
        return datePresence;
    }

    public void setDatePresence(Timestamp datePresence) {
        this.datePresence = datePresence;
    }

    public double getNombrePersonne() {
        return nombrePersonne;
    }

    public void setNombrePersonne(double nombrePersonne) {
        this.nombrePersonne = nombrePersonne;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
    }
}
