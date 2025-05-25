package com.entreprise.msexpense.repositories;

import com.entreprise.msexpense.entities.RapportDepense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RapportDepenseRepository extends JpaRepository<RapportDepense,Long> {
}
