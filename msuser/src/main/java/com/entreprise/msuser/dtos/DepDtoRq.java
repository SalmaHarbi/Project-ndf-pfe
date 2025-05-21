package com.entreprise.msuser.dtos;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DepDtoRq {

    @NotEmpty(message = "nom should not be null")
    private String nom;
    private String image;

    @NotEmpty(message = "centre de cout should not be null")
    private String centredecout;
}