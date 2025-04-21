package com.entreprise.msuser.controllers;


import com.entreprise.msuser.dtos.LoginDto;
import com.entreprise.msuser.dtos.TokenDto;
import com.entreprise.msuser.dtos.refreshTokenDto;
import com.entreprise.msuser.services.serviceimpl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthServiceImpl authService;

    public AuthController(AuthServiceImpl authService){
        this.authService=authService;
    }

    @PostMapping("/login")
    public TokenDto login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }



    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody refreshTokenDto refreshToken) {
        authService.logout(refreshToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
