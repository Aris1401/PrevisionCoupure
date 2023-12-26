package com.panneau.coupure.repository;

import com.panneau.coupure.model.Presence;
import com.panneau.coupure.model.Salle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface PresenceRepository extends CrudRepository<Presence, Long> {
    @Query("SELECT p FROM Presence p WHERE p.datePresence < ?1 AND p.salle = ?2")
    public List<Presence> findByDatePresenceBeforeOrEqualsAndSalle(Timestamp dateBefore, Salle salle);

    @Query("SELECT p FROM Presence p WHERE DATE(p.datePresence) = DATE(?1)")
    public List<Presence> findByHistoriqueCoupureDate(Timestamp dateEnregistree);
}
