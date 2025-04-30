package com.entreprise.msuser.services;

import com.entreprise.msuser.dtos.LoginDto;
import com.entreprise.msuser.dtos.ResetPassword;
import com.entreprise.msuser.dtos.TokenDto;
import com.entreprise.msuser.dtos.refreshTokenDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Map;


@Service
public interface AuthInterface {
    TokenDto login(LoginDto loginDto);
    void logout(refreshTokenDto refreshTokenDto);
    Map<String, Object> refreshAccessToken(refreshTokenDto refreshToken);
    ResponseEntity<String> resetPassword(ResetPassword request,Principal principal);
    ResponseEntity<String> forgotPassword( String email);

}
