package com.panneau.coupure.repository;

import com.panneau.coupure.model.SourceSolaire;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceSolaireRepository extends CrudRepository<SourceSolaire, Long> {
}
