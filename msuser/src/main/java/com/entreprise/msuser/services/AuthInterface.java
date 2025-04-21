package com.entreprise.msuser.services;

import com.entreprise.msuser.dtos.LoginDto;
import com.entreprise.msuser.dtos.TokenDto;
import com.entreprise.msuser.dtos.refreshTokenDto;
import org.springframework.stereotype.Service;


@Service
public interface AuthInterface {
    TokenDto login(LoginDto loginDto);
    void logout(refreshTokenDto refreshTokenDto);
}
