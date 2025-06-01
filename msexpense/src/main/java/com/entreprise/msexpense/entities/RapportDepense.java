package com.entreprise.msexpense.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class RapportDepense {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateGeneration;

    private String cheminRapport;

    private String raison;
    private Boolean Statut=true;

    @OneToOne
    @JoinColumn(name = "depense_id", referencedColumnName = "id")
    @JsonBackReference
    private Depense depense;


}
