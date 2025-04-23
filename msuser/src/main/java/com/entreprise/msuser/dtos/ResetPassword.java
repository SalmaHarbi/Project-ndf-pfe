package com.entreprise.msuser.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class ResetPassword {
    private String oldPassword;
    private String newPassword;
}
