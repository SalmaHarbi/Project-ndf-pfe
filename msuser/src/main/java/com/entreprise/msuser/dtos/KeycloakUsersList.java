package com.entreprise.msuser.dtos;

import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder


public class KeycloakUsersList {

    private String firstName;
    private String lastName;
    private String username;
    private String departementNom;
    private String email;
    private List<String> roles;

}
