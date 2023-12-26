package com.panneau.coupure.service;

import com.panneau.coupure.dto.*;
import com.panneau.coupure.exceptions.LimiteConsommationException;
import com.panneau.coupure.model.*;
import com.panneau.coupure.repository.BatterieRepository;
import com.panneau.coupure.repository.PanneauRepository;
import com.panneau.coupure.repository.SecteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class PrevisionService {
    @Autowired
    private SourceSolaireService sourceSolaireService;

    @Autowired
    private SecteurRepository secteurRepository;
    @Autowired
    private BatterieRepository batterieRepository;
    @Autowired
    private PanneauRepository panneauRepository;

    @Autowired
    SecteurSourceBufferService secteurSourceBufferService;

    @Autowired
    private LuminositeService luminositeService;
    @Autowired
    private PresenceService presenceService;

    private HistoriqueCoupureService historiqueCoupureService;
    @Autowired
    public void setHistoriqueCoupureService(@Lazy HistoriqueCoupureService historiqueCoupureService) {
        this.historiqueCoupureService = historiqueCoupureService;
    }

    public Prevision prevoirPourDate(Timestamp datePrevision, String luminosite) {
        double consommationMoyenneEtudiants = historiqueCoupureService.getConsommationMoyenne(datePrevision);
        System.out.println("");
        System.out.println("---------------------Details----------------:");
        return getHeureDeCoupurePour(datePrevision, consommationMoyenneEtudiants, luminosite, null);
    }

    /**
     * Calcule l'heure a laquelle l'elecricite pourrait se couper.
     * @param dateProposee Date proposee pour l'heure de coupure.
     * @param consommationMoyenneEtudiant La consommation moyenne de tout les etudiants.
     * @param luminosite La liste de luminosite du jour donnee.
     * @param secteurs La liste des secteurs pour le calcul.
     * @return L'heure a laquelle l'electricite sera couper.
     */
    public Prevision getHeureDeCoupurePour(Timestamp dateProposee, double consommationMoyenneEtudiant, String luminosite, Presence[] presences, Secteur... secteurs) {
        // Prevision de coupure
        Prevision prevision = new Prevision();
        prevision.setDatePrevision(dateProposee);

        // Obtenir tout les secteurs
        Iterable<Secteur> secteursPresents = secteurRepository.findAll();

        // Charger toutes les sources solaires de chaque secteur
        SecteurSourceBuffer secteurSourceBuffer = loadSourcesInBuffer();

        // Debut heure TODO: Changer avec les valeurs en configuration
        int debutHeure = 8;
        int finHeure = 17;
        int differenceHeure = finHeure - debutHeure;
        int[] heures = new int[] {8,9,10,11,14,15,16,17};

        // Obtenir luminosite
        ArrayList<Luminosite> luminosites = luminositeService.parseLuminosite(luminosite);

        // Besoin etudiant
        double besoinEtudiant = 0;

        // Obtenir jour de la semaine
        LocalDateTime datePrevision = dateProposee.toLocalDateTime();
        DayOfWeek dayOfPrevision = datePrevision.getDayOfWeek();
        int jourCourant = dayOfPrevision.getValue();
        System.out.println("JOUR: " + jourCourant + " = " + dateProposee);

        for (Secteur secteur : secteursPresents) {
            // Obtenir presence
            Presence[] presenceEtudiant = presences == null ? presenceService.faireMoyennePresence(secteur, JourDeLaSemaine.getJourFromNumero(jourCourant), dateProposee) : presences;

            besoinEtudiant = presenceEtudiant[0].getNombrePersonne() * consommationMoyenneEtudiant;
            for (int i = 0; i < heures.length; i++) {
                // Obtenir heure courante
                double heureCourante = heures[i];

                System.out.println("Conso moyenne: " + consommationMoyenneEtudiant);

                if (heureCourante > 12) {
                    // TODO: Remplacer 12 par valeur en base
                    besoinEtudiant = presenceEtudiant[1].getNombrePersonne() * consommationMoyenneEtudiant;
                    System.out.println("Presence: " + presenceEtudiant[1].getNombrePersonne());
                } else {
                    System.out.println("Presence: " + presenceEtudiant[0].getNombrePersonne());
                }

                System.out.println("Heure: " + heureCourante + " | Luminosite: " + luminosites.get(i).getNiveauLuminosite());

                DetailsCoupure detailsCoupure = new DetailsCoupure();
                detailsCoupure.setSecteur(secteur);
                detailsCoupure.setConsommationMoyenneEtudiant(consommationMoyenneEtudiant);
                detailsCoupure.setPresences(presenceEtudiant);

                try {
                    System.out.println("Besoin etudiant: " + besoinEtudiant);
                    secteurSourceBufferService.consommerPanneau(secteurSourceBuffer, secteur.getId(), besoinEtudiant, luminosites.get(i), heureCourante);
                } catch (LimiteConsommationException e) {
                    // Obtenir heure de coupure
                    ConsommationBatterie consommationBatterie = e.getConsommationBatterie();
                    double neededConsommation = e.getNeededConsommation();
                    System.out.println("TAPAKA: " + consommationBatterie.getHeure());

//                    Calendar calendar = getCalendar(dateProposee, consommationBatterie, neededConsommation);


                    // Convertir
                    double hourInMinutes = 60;
                    double consommationMinutes = (consommationBatterie.getConsommation() * hourInMinutes) / neededConsommation;
                    int minutes = (int) consommationMinutes;

                    double decimal = consommationMinutes - minutes;
                    double seconds = decimal * 60;

                    LocalDateTime dateTime = LocalDateTime.ofInstant(dateProposee.toInstant(), ZoneId.systemDefault())
                                    .withHour((int) consommationBatterie.getHeure())
                                            .withMinute(minutes)
                                                    .withSecond((int) seconds);
                    System.out.println("Date: " + dateTime + " Heure: " + consommationBatterie.getHeure());

                    prevision.setDateCoupure(Timestamp.valueOf(dateTime));
                }
            }
        }

        prevision.setConsommationBatteries(secteurSourceBuffer.getConsommationBatteries());
        prevision.setConsommationPanneaux(secteurSourceBuffer.getConsommationPanneaux());
        prevision.setStartHour(debutHeure);
        prevision.setEndHour(finHeure);
        prevision.setConsoMoyenneEtudiant(consommationMoyenneEtudiant);
        prevision.setLuminosites(luminosites);

        return prevision;
    }

    private static Calendar getCalendar(Timestamp dateProposee, ConsommationBatterie consommationBatterie, double neededConsommation) {
        long uneHeure = 3600000L;

        // Calcul de reste de consommation
        long consommationDuration = (long) ((consommationBatterie.getConsommation() * uneHeure) / neededConsommation);

        long targerHour = (long) ((uneHeure * consommationBatterie.getHeure()) + consommationDuration);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateProposee);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.add(Calendar.MILLISECOND, (int) targerHour);
        return calendar;
    }

//    public boolean canBatterieBePutInBuffer(Map<Integer, Batterie> batteriesBuffer, Batterie batterie) {
//        batteriesBuffer.con
//    }

    public SecteurSourceBuffer loadSourcesInBuffer() {
        SecteurSourceBuffer secteurBuffer = new SecteurSourceBuffer();

        // Ajouter les panneaux et batteries en memoire
        Iterable<Batterie> batteries = batterieRepository.findAll();
        Iterable<Panneau> panneaux = panneauRepository.findAll();

        for (Batterie batterie : batteries) {
            secteurBuffer.addBatterie(batterie);
        }

        for (Panneau panneau : panneaux) {
            secteurBuffer.addPanneau(panneau);
        }

        return secteurBuffer;
    }
}
