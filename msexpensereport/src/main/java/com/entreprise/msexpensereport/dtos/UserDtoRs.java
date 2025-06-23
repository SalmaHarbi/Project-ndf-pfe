package com.entreprise.msexpensereport.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDtoRs {


    private Long id;
    @NotEmpty(message = "firstname should not be null")
    private String firstname;

    @NotEmpty(message = "lastname should not be null")
    private String lastname;

    @NotEmpty(message = "email should not be null")
    private String email;

    @NotEmpty(message = "role should not be null")
    private List<String> roles;

    @NotEmpty(message = "Name Department should not be null")
    private String departementNom;

    private String photo;



}
