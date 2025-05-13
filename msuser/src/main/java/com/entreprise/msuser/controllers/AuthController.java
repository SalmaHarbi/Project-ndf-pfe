package com.entreprise.msuser.controllers;


import com.entreprise.msuser.dtos.*;
import com.entreprise.msuser.services.serviceimpl.AuthServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
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

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles(@RequestHeader("Authorization") String authHeader) {
        String token = authHeader.replace("Bearer ", "");
        List<String> roles = authService.getUserRoles(token);
        return ResponseEntity.ok(roles);
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

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return authService.getAllUsers();
    }



}
