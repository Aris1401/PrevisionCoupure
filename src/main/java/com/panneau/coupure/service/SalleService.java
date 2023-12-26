package com.panneau.coupure.service;

import com.panneau.coupure.dto.JourDeLaSemaine;
import com.panneau.coupure.model.Presence;
import com.panneau.coupure.model.Salle;
import com.panneau.coupure.repository.PresenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class SalleService {
    @Autowired
    private PresenceRepository presenceRepository;

    public int getJourDePresence(Presence presence) {
        LocalDateTime datePresence = presence.getDatePresence().toLocalDateTime();
        DayOfWeek dayOfWeek = datePresence.getDayOfWeek();

        return dayOfWeek.getValue();
    }

    public Presence[] moyennePresence(Salle salle, JourDeLaSemaine jour, Timestamp dateReference) {
        // Prensences avant date specifiee
        List<Presence> presences = presenceRepository.findByDatePresenceBeforeOrEqualsAndSalle(dateReference, salle);

        double nombreTotalMatin = 0;
        double nombreTotalApresMidi = 0;

        double realSize = 0;
        for (Presence presence : presences) {
            if (presence.getDatePresence().toLocalDateTime().getDayOfWeek().getValue() != jour.getNumeroJour()) continue;
            LocalDateTime presenceDate = presence.getDatePresence().toLocalDateTime();

            // TODO: Changer si il y nouvelle tranche d'heure
            if (presenceDate.getHour() <= 12) {
                nombreTotalMatin += presence.getNombrePersonne();
                realSize++;
            }
            else nombreTotalApresMidi += presence.getNombrePersonne();
        }

        nombreTotalMatin /= (double) realSize;
        nombreTotalApresMidi /= (double) realSize;

        // Creation de nouvelles presences
        Presence presenceMatin = new Presence(nombreTotalMatin);
        Presence presenceApreMidi = new Presence(nombreTotalApresMidi);

        return new Presence[] {presenceMatin, presenceApreMidi};
    }
}
