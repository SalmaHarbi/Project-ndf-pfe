package com.entreprise.msuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDepDto {
    private String nom;
    private String prenom;
    private String email;
    private String role;
}
