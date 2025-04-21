package com.entreprise.msexpense.entities;

import com.entreprise.msexpense.dtos.NdfRs;
import com.entreprise.msexpense.entities.Enum.Categorie;
import com.entreprise.msexpense.entities.Enum.Indicateurfiscabilte;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@AllArgsConstructor @NoArgsConstructor
@Data
@Entity
public class Depense {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime datedepense = LocalDateTime.now();

    @Enumerated(EnumType.STRING)
    private Categorie categorie;

    private String description;
    private BigDecimal montant;
    private String device;
    private BigDecimal tauxchange;
    private BigDecimal montantconverti;
    private String nomfournisseur;
    private String commentaire;
    private Boolean statut;

    private Long ndfId;


    @Enumerated(EnumType.STRING)
    private Indicateurfiscabilte indicateurfiscabilte;

    @OneToMany(mappedBy = "depense")
    private Collection<Justificatif> justificatifs;

    @Transient
    private NdfRs ndfRs;
}
