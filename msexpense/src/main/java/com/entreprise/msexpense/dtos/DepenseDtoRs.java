package com.entreprise.msexpense.dtos;

import com.entreprise.msexpense.entities.Enum.Categorie;
import com.entreprise.msexpense.entities.Enum.Indicateurfiscabilte;
import com.entreprise.msexpense.entities.Justificatif;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepenseDtoRs {
    private Long id;

    @NotNull
    private LocalDateTime datedepense;
    @NotNull
    private Categorie categorie;
    @NotEmpty
    private String description;
    @NotNull
    private BigDecimal montant;
    @NotNull
    private String device;
    @NotNull
    private BigDecimal tauxchange;
    private BigDecimal montantconverti;
    @NotEmpty
    private String nomfournisseur;
    private String commentaire;
    @NotNull
    private Indicateurfiscabilte indicateurfiscabilte;
    private Boolean statut;
    private Long ndfId;
    private Ndfs ndfs;
    private Collection<Justificatif> justificatifs;

}