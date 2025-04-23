package com.entreprise.msexpense.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NdfRs {
    @NotEmpty
    private String titre;
    private LocalDateTime datesoumission=LocalDateTime.now();
    @NotEmpty
    private String motifnotefrais;
    private String commentaire;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateapprobationrejet;
    @NotEmpty
    private String raisondurejet;
    @NotNull
    private Float montanttotal;
    private Long UserId;
    private NdfRs ndfRs;





}
