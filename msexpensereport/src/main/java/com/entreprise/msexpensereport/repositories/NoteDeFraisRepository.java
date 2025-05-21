package com.entreprise.msexpensereport.repositories;

import com.entreprise.msexpensereport.entities.Enum.Statut;
import com.entreprise.msexpensereport.entities.NoteDeFrais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteDeFraisRepository extends JpaRepository<NoteDeFrais,Long> {

    @Query("SELECT c FROM NoteDeFrais c WHERE c.statutDisable = :statutDisable AND c.statut = :statut")
    List<NoteDeFrais> findAllNoteDeFraisByStatutAndStatutDisable(@Param("statutDisable") Boolean statutDisable, @Param("statut") Statut statut);

    @Query("SELECT COUNT(n) FROM NoteDeFrais n WHERE n.statut = :statut")
    Long countNoteDeFraisByStatut(@Param("statut") Statut statut);


    @Query(value = "SELECT EXTRACT(YEAR FROM datesoumission) as annee, " + "EXTRACT(MONTH FROM datesoumission) as mois, " + "statut, COUNT(*) as total " +
            "FROM note_de_frais " +
            "WHERE statut_disable = true " + "GROUP BY annee, mois, statut " + "ORDER BY annee, mois", nativeQuery = true)
    List<Object[]> countByMonthAndStatut();




}
