package com.entreprise.msexpensereport.dtos;

import com.entreprise.msexpensereport.entities.Enum.Categorie;
import com.entreprise.msexpensereport.entities.Enum.Indicateurfiscabilte;
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


    @NotNull
    private LocalDateTime datedepense;

    private String nom;

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
    private Collection<JustificatifDto> justificatifs;



}