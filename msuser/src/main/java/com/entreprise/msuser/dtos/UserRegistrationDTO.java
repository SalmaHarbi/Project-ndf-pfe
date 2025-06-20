package com.entreprise.msuser.dtos;


import lombok.*;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor(force = true)
@Builder
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;
    private List<String> roles;

    @NonNull
    private String departementNom;

    @NonNull
    private String email;
    private String password;


}
