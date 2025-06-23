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

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DepenseDtoRq {

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
    @NotEmpty
    private String nomfournisseur;
    private String commentaire;

    @NotNull
    private Indicateurfiscabilte indicateurfiscabilte;

    private Collection<JustificatifDto> justificatifs;

    private Long ndfId;

}
