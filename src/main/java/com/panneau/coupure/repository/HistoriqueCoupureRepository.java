package com.panneau.coupure.repository;

import com.panneau.coupure.model.HistoriqueCoupure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public interface HistoriqueCoupureRepository extends CrudRepository<HistoriqueCoupure, Long> {
    public Iterable<HistoriqueCoupure> findByDateEnregistrementLessThanEqual(Timestamp date);
}
