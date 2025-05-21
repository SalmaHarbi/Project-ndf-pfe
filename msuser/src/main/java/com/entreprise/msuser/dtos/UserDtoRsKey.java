package com.entreprise.msuser.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserDtoRsKey {

    private String firstName;
    private String lastName;
    private String username;
    private String department;
    private String email;
    private List<String> roles;





}
