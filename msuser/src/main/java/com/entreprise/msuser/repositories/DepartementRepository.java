package com.entreprise.msuser.repositories;

import com.entreprise.msuser.entities.Departement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartementRepository extends JpaRepository<Departement,Long> {
    Optional<Departement> findByNom(String nom);


}
