package com.entreprise.msuser.services;


import com.entreprise.msuser.dtos.*;
import com.entreprise.msuser.entities.Utilisateur;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UtilisateurService {


    UserDtoRs getById(Long id);
    List<UserDepDto> getAllUtilisateur();
    ApiResponse addUser(UserDtoRq userDtoRq);
    ApiResponse deleteUser(Long id);
    ApiResponse updateUser(Long id,UserDtoRq userDtoRq);
    ResponseEntity<List<KeycloakUsersList>> getAllUsers();
    List<Utilisateur> signUp(UserRegistrationDTO dto);

    }
