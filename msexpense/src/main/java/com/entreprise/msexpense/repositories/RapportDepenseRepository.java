package com.entreprise.msexpense.repositories;

import com.entreprise.msexpense.entities.RapportDepense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportDepenseRepository extends JpaRepository<RapportDepense,Long> {
    @Query("SELECT c FROM Depense c WHERE c.statut=:statut")
    List<RapportDepense> findAllRapportDepenseByStatut(@Param("statut") Boolean statut);
}
