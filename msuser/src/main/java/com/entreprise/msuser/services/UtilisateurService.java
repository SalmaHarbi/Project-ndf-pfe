package com.entreprise.msuser.services;


import com.entreprise.msuser.dtos.ApiResponse;
import com.entreprise.msuser.dtos.UserDepDto;
import com.entreprise.msuser.dtos.UserDtoRq;
import com.entreprise.msuser.dtos.UserDtoRs;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UtilisateurService {


    UserDtoRs getById(Long id);
    List<UserDepDto> getAllUtilisateur();
    ApiResponse addUser(UserDtoRq userDtoRq);
    ApiResponse deleteUser(Long id);
    ApiResponse updateUser(Long id,UserDtoRq userDtoRq);
}
