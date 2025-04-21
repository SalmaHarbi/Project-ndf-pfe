package com.entreprise.msuser.dtos;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenDto {
    private String accessToken;
    private String refreshToken;
    private Long expIn;
    private Long refExpIn;
    private Long refreshExpiresIn;
    private String tokenType;


}
