package com.panneau.coupure.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.panneau.coupure.model.Presence;
import com.panneau.coupure.model.Secteur;

import java.sql.Array;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Prevision {
    /**
     * Date a laquelle la prevision a ete faite.
     */
    Timestamp datePrevision;
    ArrayList<Luminosite> luminosites;
    Timestamp dateCoupure;

    double startHour = 8;
    double endHour = 17;

    // Details coupure
    double consoMoyenneEtudiant = 0;
    ArrayList<ConsommationBatterie> consommationBatteries = new ArrayList<>();
    ArrayList<ConsommationPanneau> consommationPanneaux = new ArrayList<>();

    public Timestamp getDatePrevision() {
        return datePrevision;
    }

    public void setDatePrevision(Timestamp datePrevision) {
        this.datePrevision = datePrevision;
    }

    public ArrayList<Luminosite> getLuminosites() {
        return luminosites;
    }

    public void setLuminosites(ArrayList<Luminosite> luminosites) {
        this.luminosites = luminosites;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    public Timestamp getDateCoupure() {
        return dateCoupure;
    }
    public String getDateCoupureString() {
        return dateCoupure.toLocalDateTime().toString();
    }
    public void setDateCoupure(Timestamp dateCoupure) {
        this.dateCoupure = dateCoupure;
    }

    public double getStartHour() {
        return startHour;
    }

    public void setStartHour(double startHour) {
        this.startHour = startHour;
    }

    public double getEndHour() {
        return endHour;
    }

    public void setEndHour(double endHour) {
        this.endHour = endHour;
    }

    public double getConsoMoyenneEtudiant() {
        return consoMoyenneEtudiant;
    }

    public void setConsoMoyenneEtudiant(double consoMoyenneEtudiant) {
        this.consoMoyenneEtudiant = consoMoyenneEtudiant;
    }

    public ArrayList<ConsommationBatterie> getConsommationBatteries() {
        return consommationBatteries;
    }

    public void setConsommationBatteries(ArrayList<ConsommationBatterie> consommationBatteries) {
        this.consommationBatteries = consommationBatteries;
    }

    public ArrayList<ConsommationPanneau> getConsommationPanneaux() {
        return consommationPanneaux;
    }

    public void setConsommationPanneaux(ArrayList<ConsommationPanneau> consommationPanneaux) {
        this.consommationPanneaux = consommationPanneaux;
    }
}
