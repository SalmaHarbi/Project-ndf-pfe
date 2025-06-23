package com.entreprise.msuser.controllers;

import com.entreprise.msuser.dtos.*;
import com.entreprise.msuser.entities.Utilisateur;
import com.entreprise.msuser.services.UtilisateurService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
@Slf4j
public class UtilisateurController {

    private final UtilisateurService utilisateurService;

    public UtilisateurController(UtilisateurService utilisateurService){
         this.utilisateurService=utilisateurService;
    }


    @GetMapping("/get/{id}")
    public KeycloakUsersList getById(@PathVariable("id") Long id) {
        return utilisateurService.getById(id);
    }

    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<KeycloakUsersList>> getUsersByRole(@PathVariable String roleName) {
        return utilisateurService.getUsersByRole(roleName);
    }
    @GetMapping("/getAllEmplDep")
    public List<UserDepDto> getAllUtilisateur() {
        return utilisateurService.getAllUtilisateur();
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<KeycloakUsersList> getUserByUsername(@PathVariable String username) {
        KeycloakUsersList userDto = utilisateurService.findByUsername(username);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("/add")
    public ApiResponse createUser(@RequestBody UserDtoRq userDtoRq) {
        return utilisateurService.addUser(userDtoRq);
    }

    @PutMapping("/update/{id}")
    public ApiResponse updateUser(@PathVariable("id") Long id, @RequestBody UserDtoRq userDtoRq) {
        return utilisateurService.updateUser(id,userDtoRq);
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteUser(@PathVariable("id") Long id) {
        return utilisateurService.deleteUser(id);
    }

    @GetMapping("/auth")
    public Authentication authentication(Authentication authentication){
        return authentication;
    }


    @GetMapping("/all")
    public ResponseEntity<List<KeycloakUsersList>> getAllUsers(){
        return utilisateurService.getAllUsers();
    }


    @PostMapping("/signup")
    public ResponseEntity<List<Utilisateur>> signUp(@RequestBody UserRegistrationDTO signUpRequest) {
        try {
            List<Utilisateur> createdUsers = utilisateurService.signUp(signUpRequest);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdUsers);
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }


}
