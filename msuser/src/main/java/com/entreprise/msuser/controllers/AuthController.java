package com.entreprise.msuser.controllers;


import com.entreprise.msuser.dtos.LoginDto;
import com.entreprise.msuser.dtos.ResetPassword;
import com.entreprise.msuser.dtos.TokenDto;
import com.entreprise.msuser.dtos.refreshTokenDto;
import com.entreprise.msuser.services.serviceimpl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

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

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestParam refreshTokenDto refreshToken) {
        try {
            Map<String, Object> response = authService.refreshAccessToken(refreshToken);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
        }
    }
    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPassword request,
                                                Principal principal) {
        return authService.resetPassword(request, principal);
    }

    @PostMapping("/password/forgot")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return authService.forgotPassword(email);
    }


}
