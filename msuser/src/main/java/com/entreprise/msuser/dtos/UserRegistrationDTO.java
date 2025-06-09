package com.entreprise.msuser.dtos;


import lombok.*;


@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor(force = true)
@Builder
public class UserRegistrationDTO {
    private String firstName;
    private String lastName;
    private String username;

    @NonNull
    private String departementNom;

    @NonNull
    private String email;
    private String password;


}
