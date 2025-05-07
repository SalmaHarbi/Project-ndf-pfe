package com.entreprise.msuser.services.serviceimpl;

import com.entreprise.msuser.dtos.ApiResponse;
import com.entreprise.msuser.dtos.UserDepDto;
import com.entreprise.msuser.dtos.UserDtoRq;
import com.entreprise.msuser.dtos.UserDtoRs;
import com.entreprise.msuser.entities.Departement;
import com.entreprise.msuser.entities.Utilisateur;
import com.entreprise.msuser.mappers.UtilisateurMapper;
import com.entreprise.msuser.repositories.DepartementRepository;
import com.entreprise.msuser.repositories.UtilisateurRepository;
import com.entreprise.msuser.services.UtilisateurService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final DepartementRepository departementRepository;


    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository,UtilisateurMapper utilisateurMapper, DepartementRepository departementRepository){
        this.utilisateurRepository=utilisateurRepository;
        this.utilisateurMapper=utilisateurMapper;
        this.departementRepository=departementRepository;
    }



    @Override
    public UserDtoRs getById(Long id) {
        Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
        return utilisateurMapper.toDtoRs(utilisateur);
    }

    @Override
    public List<UserDepDto> getAllUtilisateur() {
        List<Utilisateur> utilisateurs = utilisateurRepository.findAllUtilisateurByStatut(true);

        utilisateurs.sort(Comparator.comparing(
                utilisateur -> {
                    Departement dept = utilisateur.getDepartement();
                    return (dept != null && dept.getNom() != null) ? dept.getNom().toLowerCase() : "";
                },
                Comparator.nullsLast(String::compareTo)
        ));

        return utilisateurs.stream()
                .map(utilisateurMapper::toUtilisateurLightDto)
                .collect(Collectors.toList());
    }

    @Override
    public ApiResponse addUser(UserDtoRq userDtoRq) {
        Utilisateur utilisateur = utilisateurMapper.toEntity(userDtoRq);

        // Vérifier si un département a été fourni
        if (userDtoRq.getDepartementId() != null) {
            Departement departement = departementRepository.findById(userDtoRq.getDepartementId())
                    .orElseThrow(() -> new RuntimeException("Département introuvable"));
            utilisateur.setDepartement(departement);
        } else {
            utilisateur.setDepartement(null); // ou ignorer cette ligne
        }

        utilisateurRepository.save(utilisateur);

        return ApiResponse.builder()
                .id(utilisateur.getId())
                .message("User has been saved successfully")
                .build();
    }

    @Override
    public ApiResponse deleteUser(Long id) {
     Utilisateur utilisateur = utilisateurRepository.findById(id).orElse(null);
     utilisateur.setStatut(false);
     utilisateurRepository.save(utilisateur);
     return ApiResponse.builder()
             .id(utilisateur.getId())
             .message("User has been deleted successfuly")
             .build();

    }

    @Override
    public ApiResponse updateUser(Long id,UserDtoRq userDtoRq) {
       Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
        utilisateurMapper.partialUpdate(userDtoRq,utilisateur);
       utilisateurRepository.save(utilisateur);
       return ApiResponse.builder()
               .id(utilisateur.getId())
               .message("User has been updated successfuly")
               .build();
    }
}
