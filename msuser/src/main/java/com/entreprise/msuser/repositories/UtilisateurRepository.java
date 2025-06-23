package com.entreprise.msuser.repositories;

import com.entreprise.msuser.entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur,Long> {

    Utilisateur findUtilisateurByUsername(String username);
  

    @Query("SELECT c FROM Utilisateur c WHERE c.statut=:statut")
    List<Utilisateur> findAllUtilisateurByStatut(@Param("statut") Boolean statut);


    Optional<Utilisateur> findByUsername(String username);

}
