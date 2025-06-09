package com.entreprise.msuser.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoRq {

    private Long id ;

    @NotEmpty(message = "lastname should not be null")
    private String lastname;

    @NotEmpty(message = "firstname should not be null")
    private String firstname;

    @NotEmpty(message = "email should not be null")
    private String email;

    @NotEmpty(message = "password should not be null")
    private String password;

    @NotEmpty(message = "role should not be null")
    private List<String> roles;

    private String photo;

    @NotEmpty(message = "Name Department should not be null")
    private String departementNom;





}
