package com.panneau.coupure.repository;

import com.panneau.coupure.model.Panneau;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanneauRepository extends CrudRepository<Panneau, Long> {
}
