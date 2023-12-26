package com.panneau.coupure.service;

import com.panneau.coupure.dto.Prevision;
import com.panneau.coupure.model.HistoriqueCoupure;
import com.panneau.coupure.model.Presence;
import com.panneau.coupure.repository.HistoriqueCoupureRepository;
import com.panneau.coupure.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class HistoriqueCoupureService {
    @Autowired
    private HistoriqueCoupureRepository historiqueCoupureRepository;
    @Autowired
    private PresenceRepository presenceRepository;

    @Autowired
    private PrevisionService previsionService;

    public Presence[] getPresencesPourHistorique(HistoriqueCoupure historiqueCoupure) {
        List<Presence> presences = presenceRepository.findByHistoriqueCoupureDate(historiqueCoupure.getDateEnregistrement());

        double moyenneMatin = 0;
        double moyenneApresMidi = 0;
        for (Presence presence : presences) {
            LocalDateTime presenceDate = presence.getDatePresence().toLocalDateTime();

            // TODO: Si il y a nouvelle tranche d'heure
            if (presenceDate.getHour() <= 12) moyenneMatin += presence.getNombrePersonne();
            else moyenneApresMidi += presence.getNombrePersonne();
        }

//        moyenneMatin /= presences.size();
//        moyenneApresMidi /= presences.size();

        // Presences
        Presence presenceMatin = new Presence(moyenneMatin);
        Presence presenceApresMidi = new Presence(moyenneApresMidi);

        return new Presence[] { presenceMatin, presenceApresMidi };
    }

    public double getConsommationPourHistorique(HistoriqueCoupure historiqueCoupure) {
        // TODO: Posons la consommation par defaut
        double a_consommationEtudiant = 60;
        double b_consommationEtudiant = 0;

        double marge = 2;

        LocalDateTime dateCoupure = historiqueCoupure.getDateCoupure().toLocalDateTime();

        //<editor-fold desc="Calcul initial pour A">
        Presence[] presences = getPresencesPourHistorique(historiqueCoupure);
        System.out.println("Matin: " + presences[0].getNombrePersonne() + " | Aprem: " + presences[1].getNombrePersonne());
        Prevision prevision = previsionService.getHeureDeCoupurePour(historiqueCoupure.getDateEnregistrement(), a_consommationEtudiant, historiqueCoupure.getLuminosite(), getPresencesPourHistorique(historiqueCoupure));

        while (prevision.getDateCoupure() == null) {
            a_consommationEtudiant += 0.2;
            prevision = previsionService.getHeureDeCoupurePour(historiqueCoupure.getDateEnregistrement(), a_consommationEtudiant, historiqueCoupure.getLuminosite(), getPresencesPourHistorique(historiqueCoupure));
        }

        LocalTime a_previsionDate = prevision.getDateCoupure().toLocalDateTime().toLocalTime();

        if (a_previsionDate.isAfter(dateCoupure.toLocalTime())) {
            b_consommationEtudiant = a_consommationEtudiant * marge;
        } else if (a_previsionDate.isBefore(dateCoupure.toLocalTime())) {
            b_consommationEtudiant = a_consommationEtudiant;
            a_consommationEtudiant /= marge;
        } else {
            return a_consommationEtudiant;
        }
        //</editor-fold>

        double limite = 10e-7;
        double x_consommation = 0;
        while (Math.abs(a_consommationEtudiant - b_consommationEtudiant) > limite) {
            x_consommation = (a_consommationEtudiant + b_consommationEtudiant) / marge;

            prevision = previsionService.getHeureDeCoupurePour(historiqueCoupure.getDateEnregistrement(), x_consommation, historiqueCoupure.getLuminosite(), getPresencesPourHistorique(historiqueCoupure));

            System.out.println("a(" + a_consommationEtudiant + ") + b(" + b_consommationEtudiant + ")\nx(" + x_consommation + ")");
            LocalDateTime x_previsionDate = prevision.getDateCoupure().toLocalDateTime();

            System.out.println("Prevision: " + prevision.getDateCoupure().toLocalDateTime() + " - Vraie: " + dateCoupure);
            if (x_previsionDate.isAfter(dateCoupure)) {
                a_consommationEtudiant = x_consommation;
            } else if (x_previsionDate.isBefore(dateCoupure)) {
                b_consommationEtudiant = x_consommation;
            } else {
                return x_consommation;
            }
        }

        return x_consommation;
    }

    /**
     * Obtenir la consommation moyenne des etudiants a partir d'une date donnee.
     * @param dateEnPrior Date specifier pour filtrer les historique.
     * @return La consommation moyenne des etudiants.
     */
    public double getConsommationMoyenne(Timestamp dateEnPrior) {
        // Obtenir tout les historiques de coupures
        Iterable<HistoriqueCoupure> historiqueCoupures = historiqueCoupureRepository.findByDateEnregistrementLessThanEqual(dateEnPrior);

        ArrayList<String> prints = new ArrayList<>();
        double moyenneConsommation = 0;
        double size = 0;
        for (HistoriqueCoupure historiqueCoupure : historiqueCoupures) {
            double conso = getConsommationPourHistorique(historiqueCoupure);
            prints.add("!!!!!!!!!!!!!!!!!!!!!!: " + historiqueCoupure.getDateEnregistrement() + " : " + conso);
//            System.out.println();
            moyenneConsommation += conso;
            size++;
        }

        System.out.println("\n\n\n\n\n\n\n");
        for (String print : prints) {
            System.out.println(print);
        }

        moyenneConsommation /= size;

        return moyenneConsommation;
    }
}
