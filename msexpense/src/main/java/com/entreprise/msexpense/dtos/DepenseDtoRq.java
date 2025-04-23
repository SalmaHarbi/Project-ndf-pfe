package com.entreprise.msexpense.dtos;

import com.entreprise.msexpense.entities.Enum.Categorie;
import com.entreprise.msexpense.entities.Enum.Indicateurfiscabilte;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepenseDtoRq {
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
    private Long ndfId;
    @NotNull
    private Indicateurfiscabilte indicateurfiscabilte;
    private Boolean statut;
}
