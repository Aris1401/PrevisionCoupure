package com.panneau.coupure.dto;

public enum JourDeLaSemaine {
    LUNDI(1),
    MARDI(2),
    MERCREDI(3),
    JEUDI(4),
    VENDREDI(5),
    SAMEDI(6),
    DIMANCHE(7);

    private final int numeroJour;

    JourDeLaSemaine(int numeroJour) {
        this.numeroJour = numeroJour;
    }

    public int getNumeroJour() {
        return numeroJour;
    }

    public static JourDeLaSemaine getJourFromNumero(int numeroJour) {
        for (JourDeLaSemaine jour : values()) {
            if (jour.numeroJour == numeroJour) {
                return jour;
            }
        }
        throw new IllegalArgumentException("Numéro de jour invalide : " + numeroJour);
    }
}

