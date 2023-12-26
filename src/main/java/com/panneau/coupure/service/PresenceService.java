package com.panneau.coupure.service;

import com.panneau.coupure.dto.JourDeLaSemaine;
import com.panneau.coupure.model.Presence;
import com.panneau.coupure.model.Salle;
import com.panneau.coupure.model.Secteur;
import com.panneau.coupure.repository.PresenceRepository;
import com.panneau.coupure.repository.SalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceService {
    @Autowired
    private PresenceRepository presenceRepository;
    @Autowired
    private SalleRepository salleRepository;
    @Autowired
    private SalleService salleService;

    public int getJourDePresence(Presence presence) {
        LocalDateTime datePresence = presence.getDatePresence().toLocalDateTime();
        DayOfWeek dayOfWeek = datePresence.getDayOfWeek();

        return dayOfWeek.getValue();
    }

    public Presence[] faireMoyennePresence(Secteur secteur, JourDeLaSemaine jour, Timestamp dateReference) {
        List<Salle> salles = salleRepository.findBySecteur(secteur);

        double nombreTotalMatin = 0;
        double nombreTotalApresMidi = 0;
        for (Salle salle : salles) {
            Presence[] presences = salleService.moyennePresence(salle, jour, dateReference);
            nombreTotalMatin += presences[0].getNombrePersonne();
            nombreTotalApresMidi += presences[1].getNombrePersonne();
        }

        // Creation de nouvelles presences
        Presence presenceMatin = new Presence(nombreTotalMatin);
        Presence presenceApreMidi = new Presence(nombreTotalApresMidi);

        return new Presence[] {presenceMatin, presenceApreMidi};
    }
}
