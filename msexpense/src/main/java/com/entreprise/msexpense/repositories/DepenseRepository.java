package com.entreprise.msexpense.repositories;

import com.entreprise.msexpense.entities.Depense;
import com.entreprise.msexpense.entities.Enum.Categorie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface DepenseRepository extends JpaRepository<Depense,Long> {

    @Query("SELECT c FROM Depense c WHERE c.statut=:statut")
    List<Depense> findAllDepenseByStatut(@Param("statut") Boolean statut);

    @Query("SELECT SUM(d.montantconverti) FROM Depense d WHERE d.statut = :statut")
    BigDecimal getTotalMontantConvertiByStatut(@Param("statut") Boolean statut);

    @Query("SELECT d.categorie, SUM(d.montantconverti) FROM Depense d GROUP BY d.categorie")
    List<Object[]> getDepenseMontantParCategorie();

    @Query("SELECT EXTRACT(MONTH FROM d.datedepense), SUM(d.montantconverti) " +
            "FROM Depense d WHERE d.statut = true " +
            "GROUP BY EXTRACT(MONTH FROM d.datedepense) " +
            "ORDER BY EXTRACT(MONTH FROM d.datedepense)")
    List<Object[]> getDepensesGroupByMonth();

    Optional<Depense> findByNom(String nom);


}
