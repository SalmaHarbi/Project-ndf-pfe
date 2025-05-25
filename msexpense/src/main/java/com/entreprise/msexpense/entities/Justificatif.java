package com.entreprise.msexpense.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor @NoArgsConstructor
@Data
@Entity
public class Justificatif {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomfichierordinal;
    private String cheminstockage;
    private String typemime;
    private Boolean statut=true;
    private String nom;

    private LocalDateTime dateupload = LocalDateTime.now();

    @JsonBackReference
    @ManyToOne
    private Depense depense;
}
