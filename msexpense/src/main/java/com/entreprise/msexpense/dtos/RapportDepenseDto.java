package com.entreprise.msexpense.dtos;

import com.entreprise.msexpense.entities.Depense;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RapportDepenseDto {
    private Long id;

    private LocalDate dateGeneration;

    private String cheminRapport;

    private Depense depense;
}
