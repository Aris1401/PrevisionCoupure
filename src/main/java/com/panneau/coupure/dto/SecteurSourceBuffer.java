package com.panneau.coupure.dto;

import com.panneau.coupure.model.Batterie;
import com.panneau.coupure.model.Panneau;
import com.panneau.coupure.model.Secteur;

import java.util.ArrayList;
import java.util.Map;

public class SecteurSourceBuffer {
    ArrayList<Batterie> batteries;
    ArrayList<Panneau> panneaux;

    public double reste = 0;

    // Historique de consommation
    ArrayList<ConsommationBatterie> consommationBatteries = new ArrayList<>();
    ArrayList<ConsommationPanneau> consommationPanneaux = new ArrayList<>();

    public ArrayList<Batterie> getBatteries() {
        return batteries;
    }

    public void setBatteries(ArrayList<Batterie> batteries) {
        this.batteries = batteries;
    }

    public ArrayList<Panneau> getPanneaux() {
        return panneaux;
    }

    public void setPanneaux(ArrayList<Panneau> panneaux) {
        this.panneaux = panneaux;
    }

    public boolean containsPanneau(Panneau panneau) {
        if (getPanneaux() == null) return false;
        return getPanneaux().contains(panneau);
    }

    public boolean containsBatterie(Batterie batterie) {
        if (getBatteries() == null) return false;
        return getBatteries().contains(batterie);
    }

    public void addBatterie(Batterie batterie) {
        if (getBatteries() == null) setBatteries(new ArrayList<>());
        if (containsBatterie(batterie)) return;

        getBatteries().add(batterie);
    }

    public void addPanneau(Panneau panneau) {
        if (getPanneaux() == null) setPanneaux(new ArrayList<>());
        if (containsPanneau(panneau)) return;;

        getPanneaux().add(panneau);
    }

    public int getBatterie(int idBatterie) {
        for (int i = 0; i < batteries.size(); i++) {
            if (batteries.get(i).getId() == idBatterie) return i;
        }

        return -1;
    }

    public int getPanneau(int idPanneau) {
        for (int i = 0; i < panneaux.size(); i++) {
            if (panneaux.get(i).getId() == idPanneau) return i;
        }

        return -1;
    }

    public ArrayList<ConsommationBatterie> getConsommationBatteries() {
        return consommationBatteries;
    }

    public void setConsommationBatteries(ArrayList<ConsommationBatterie> consommationBatteries) {
        this.consommationBatteries = consommationBatteries;
    }

    public double getConsommationTotalPourBatterie(int idBatterie) {
        double consommationTotal = 0;

        for (ConsommationBatterie consommationBatterie : getConsommationBatteries()) {
            if (consommationBatterie.getBatterie().getId() == idBatterie) {
                consommationTotal += consommationBatterie.getConsommation();
            }
        }

        consommationTotal -= getRechargeTotalPourBatterie(idBatterie);
        return consommationTotal;
    }

    public double getRechargeTotalPourBatterie(int idBatterie) {
        double consommation = getConsommationTotalPourBatterie(idBatterie);
        int batterieIndex = getBatterie(idBatterie);
        double currentCapacite = getBatteries().get(batterieIndex).getCapaciteMaximale();
        currentCapacite -= consommation;



        double rechargeTotal = 0;

        for (ConsommationBatterie consommationBatterie : getConsommationBatteries()) {
            if (consommationBatterie.getBatterie().getId() == idBatterie) {
                rechargeTotal += consommationBatterie.getRecharge();
            }
        }

        return rechargeTotal;
    }

    public void ajouterComsommationBatterie(ConsommationBatterie conso) {
        consommationBatteries.add(conso);
    }

    public ArrayList<ConsommationPanneau> getConsommationPanneaux() {
        return consommationPanneaux;
    }

    public void setConsommationPanneaux(ArrayList<ConsommationPanneau> consommationPanneaux) {
        this.consommationPanneaux = consommationPanneaux;
    }

    public boolean canBatterieBeConsummed(int idBatterie, double percentageLimit) {
        double totalConsommation = getConsommationTotalPourBatterie(idBatterie);

        double threshold = ( batteries.get(getBatterie(idBatterie)).getCapaciteMaximale() * percentageLimit ) / 100.0;

        if (totalConsommation >= threshold) return false;
        return true;
    }

    public void ajouterConsommationPanneaux(ConsommationPanneau consommationPanneau) {
        consommationPanneaux.add(consommationPanneau);
    }
}
