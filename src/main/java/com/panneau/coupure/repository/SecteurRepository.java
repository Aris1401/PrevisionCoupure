package com.panneau.coupure.repository;

import com.panneau.coupure.model.Salle;
import com.panneau.coupure.model.Secteur;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SecteurRepository extends CrudRepository<Secteur, Long> {
}
