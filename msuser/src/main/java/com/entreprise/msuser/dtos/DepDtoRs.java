package com.entreprise.msuser.dtos;


import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepDtoRs {

    @NotEmpty(message = "nom should not be null")
    private String nom;

    @NotEmpty(message = "centre de cout should not be null")
    private String centredecout;
    private String image;

    private Collection<UserDepDto> utilisateurs;
}
