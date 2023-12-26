package com.panneau.coupure.service;

import com.panneau.coupure.dto.Luminosite;
import com.panneau.coupure.model.Panneau;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class LuminositeService {
    public ArrayList<Luminosite> parseLuminosite(String luminosite) {
        luminosite = luminosite.substring(1, luminosite.length() - 1);

        // La liste des luminosite
        ArrayList<Luminosite> luminosites = new ArrayList<>();

        String[] luminositesString = luminosite.split(",");
        for (String luminositeString : luminositesString) {
            Luminosite l = new Luminosite(Double.parseDouble(luminositeString));
            luminosites.add(l);
        }

        return luminosites;
    }

    public double obtenirPuissancePanneau(Panneau panneau, Luminosite luminosite) {
        // TODO: Changer 10 par max luminsite
        return ( panneau.getCapaciteMaximale() * luminosite.getNiveauLuminosite() ) / 10.0;
    }
}
