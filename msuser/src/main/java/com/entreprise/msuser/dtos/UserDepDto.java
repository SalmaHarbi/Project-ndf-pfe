package com.entreprise.msuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDepDto {
    private String lastname;
    private String firstname;
    private String email;
    private List<String> roles;
}
