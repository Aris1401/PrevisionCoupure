package com.panneau.coupure.repository;

import com.panneau.coupure.model.Salle;
import com.panneau.coupure.model.Secteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SalleRepository extends CrudRepository<Salle, Long> {
    public List<Salle> findBySecteur(Secteur secteur);
}
