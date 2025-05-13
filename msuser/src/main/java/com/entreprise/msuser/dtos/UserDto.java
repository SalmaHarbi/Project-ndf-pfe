package com.entreprise.msuser.dtos;

import lombok.*;


@Getter
@Setter
@AllArgsConstructor @NoArgsConstructor
@Builder
public class UserDto {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String roles;

}
