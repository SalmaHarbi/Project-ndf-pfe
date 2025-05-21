package com.entreprise.msexpensereport.dtos;

import com.entreprise.msexpensereport.entities.Enum.Statut;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ndfs {
    private Long id;
    private String titre;
    private LocalDateTime datesoumission;
    private String motifnotefrais;
    private Statut statut;
    private String commentaire;
    private LocalDate dateapprobationrejet;
    private String raisondurejet;
    private Float montanttotal;
    private Boolean statutDisable=true;
}
