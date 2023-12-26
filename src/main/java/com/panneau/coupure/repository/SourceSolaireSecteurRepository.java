package com.panneau.coupure.repository;

import com.panneau.coupure.model.Secteur;
import com.panneau.coupure.model.SourceSolaireSecteur;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SourceSolaireSecteurRepository extends CrudRepository<SourceSolaireSecteur, Long> {
    public Iterable<SourceSolaireSecteur> findBySecteur(Secteur secteur);
}
