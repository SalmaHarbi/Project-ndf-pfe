package com.entreprise.msexpense.repositories;

import com.entreprise.msexpense.entities.Justificatif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JustificatifRepository extends JpaRepository<Justificatif,Long> {
    @Query("SELECT c FROM Justificatif c WHERE c.statut=:statut")
    List<Justificatif> findAllJustificatifByStatut(@Param("statut") Boolean statut);

}
