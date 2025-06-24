package com.entreprise.msuser.entities;


import com.entreprise.msuser.dtos.KeycloakUsersList;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Departement {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String centredecout;
    private String image;


    @OneToMany(mappedBy = "departement")
    private Collection<Utilisateur> utilisateurs;
}
