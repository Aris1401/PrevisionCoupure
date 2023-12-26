package com.panneau.coupure.repository;

import com.panneau.coupure.model.Batterie;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatterieRepository extends CrudRepository<Batterie, Long> {
}
