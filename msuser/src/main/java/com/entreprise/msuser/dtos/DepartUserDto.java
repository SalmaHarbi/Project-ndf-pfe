package com.entreprise.msuser.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepartUserDto {

    private String departName;
    private Long userCount;
}
