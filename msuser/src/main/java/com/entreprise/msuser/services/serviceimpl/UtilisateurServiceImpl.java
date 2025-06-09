package com.entreprise.msuser.services.serviceimpl;

import com.entreprise.msuser.configuration.KeycloakConfig;
import com.entreprise.msuser.dtos.*;
import com.entreprise.msuser.entities.Departement;
import com.entreprise.msuser.entities.Utilisateur;
import com.entreprise.msuser.feign.KeycloakClient;
import com.entreprise.msuser.mappers.KeycloakUtilisateurMapper;
import com.entreprise.msuser.mappers.UtilisateurMapper;
import com.entreprise.msuser.repositories.DepartementRepository;
import org.keycloak.representations.idm.UserRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import com.entreprise.msuser.repositories.UtilisateurRepository;
import com.entreprise.msuser.services.UtilisateurService;
import org.keycloak.admin.client.Keycloak;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

import static com.entreprise.msuser.constants.Constants.*;



@Service
@Slf4j

public class UtilisateurServiceImpl implements UtilisateurService {

    private final UtilisateurRepository utilisateurRepository;
    private final UtilisateurMapper utilisateurMapper;
    private final DepartementRepository departementRepository;
    private final KeycloakClient keycloakClient;
    private final KeycloakConfig keycloakConfig;
    private Keycloak keycloak;
    private final RestTemplate restTemplate;
    private final KeycloakUtilisateurMapper keycloakUtilisateurMapper;


    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurMapper utilisateurMapper, DepartementRepository departementRepository, KeycloakClient keycloakClient, KeycloakConfig keycloakConfig, RestTemplate restTemplate, KeycloakUtilisateurMapper keycloakUtilisateurMapper){
        this.utilisateurRepository=utilisateurRepository;
        this.utilisateurMapper=utilisateurMapper;
        this.departementRepository=departementRepository;
        this.keycloakClient = keycloakClient;
        this.keycloakConfig = keycloakConfig;
        this.restTemplate = restTemplate;
        this.keycloakUtilisateurMapper = keycloakUtilisateurMapper;
    }


    @Value("${authorization.realm}")
    public String realm;
    @Value("${authorization.client.id}")
    public String clientId;
    @Value("${authorization.client.secret}")
    public String clientSecret;
    @Value("${authorization.server-url}")
    public String serverUrl;


    private HttpHeaders createFormUrlEncodedHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }



    @Override
    public UserDtoRs getById(Long id) {
        Utilisateur utilisateur= utilisateurRepository.findById(id).orElse(null);
        return utilisateurMapper.toDtoRs(utilisateur);
    }



    @Override
    public ResponseEntity<List<KeycloakUsersList>> getAllUsers() {
        List<UserRepresentation> users = keycloakConfig.keycloak()
                .realms()
                .realm(realm)
                .users()
                .list();

        List<KeycloakUsersList> userDtos = users.stream()
                .map(user -> {
                    // Retrieve roles of the user
                    List<RoleRepresentation> realmRoles = keycloakConfig.keycloak()
                            .realms()
                            .realm(realm)
                            .users()
                            .get(user.getId()) // Use user's ID to fetch their roles
                            .roles()
                            .realmLevel()
                            .listEffective();

                    // Collect role names
                    List<String> roleNames = realmRoles.stream()
                            .map(RoleRepresentation::getName)
                            .toList();

                    return KeycloakUsersList.builder()
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .departementNom(user.getAttributes().get(DEPARTMENT).get(0))
                            .roles(roleNames) // Add roles to UserDto
                            .build();
                })
                .toList();



       // userDtos.forEach(user -> userRepository.save(userMapper.userDtoToUser(user)));
        return ResponseEntity.ok(userDtos);

    }




    @Override
    public List<Utilisateur> signUp(UserRegistrationDTO dto) {
        String keycloakUrl = serverUrl + ADMIN + REALMS + realm + "/users";
        String adminToken = getAdminToken(); // Get the admin token

        HttpHeaders headers = createFormUrlEncodedHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken); // Add the admin token to request's header

        // Create the object with necessary fields.
        Map<String, Object> userPayload = new HashMap<>();
        userPayload.put(USERNAME, dto.getUsername());
        userPayload.put(FIRSTNAME, dto.getFirstName());
        userPayload.put(LASTNAME, dto.getLastName());
        userPayload.put(EMAIL, dto.getEmail());
        userPayload.put(ENABLED, true);

        // Add required attributes ("department")
        Map<String, Object> attributes = new HashMap<>();
        attributes.put(DEPARTMENT, List.of(dto.getDepartementNom()));
        userPayload.put("attributes", attributes);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(userPayload, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                keycloakUrl, HttpMethod.POST, entity, String.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            log.info("User created successfully..");
            setUserPassword(dto.getUsername(), dto.getPassword(), adminToken);
        } else {
            throw new RuntimeException("Error creating user: " + response.getBody());
        }




        // Starting saving Users from Keycloak
        List<Utilisateur> listUsers;
        try {
            log.info("Retrieving users from Keycloak...");

            // Retrieve the list of users from Keycloak.
            List<UserRepresentation> users = keycloakConfig.keycloak()
                    .realms()
                    .realm(realm)
                    .users()
                    .list();

            // Map Keycloak's UserRepresentation objects to your DTO representation.
            List<KeycloakUsersList> userDtos = users.stream()
                    .map(user -> {
                        // Retrieve roles for the user.
                        List<RoleRepresentation> realmRoles = keycloakConfig.keycloak()
                                .realms()
                                .realm(realm)
                                .users()
                                .get(user.getId())
                                .roles()
                                .realmLevel()
                                .listEffective();

                        // Collect role names.
                        List<String> roleNames = realmRoles.stream()
                                .map(RoleRepresentation::getName)
                                .toList();

                        return KeycloakUsersList.builder()
                                .username(user.getUsername())
                                .firstName(user.getFirstName())
                                .lastName(user.getLastName())
                                .email(user.getEmail())
                                .departementNom(user.getAttributes().get(DEPARTMENT).get(0))
                                .roles(roleNames)
                                .build();
                    })
                    .toList();

            // Convert your DTOs to Account entities.
            listUsers = new ArrayList<>();
            for (KeycloakUsersList userDto : userDtos) {
                Utilisateur utilisateur = keycloakUtilisateurMapper.toEntity(userDto);
                // Save the new user in your database if they don't already exist.
                if (utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername()) == null) {
                    utilisateurRepository.save(utilisateur);
                }
                listUsers.add(utilisateur);
            }

            log.info("User has been saved successfully!");
        } catch (Exception e) {
            log.error("Error saving user: ", e);
            // Depending on your needs, you can either return an empty list or rethrow the exception.
            listUsers = new ArrayList<>();
        }

        // Return the list of users retrieved from Keycloak.
        return listUsers;
    }






    public void setUserPassword(String username, String password, String adminToken) {

        // Get user ID from Keycloak
        String getUserUrl = serverUrl + ADMIN + REALMS + realm + "/users?username=" + username;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(adminToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                getUserUrl,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<>() {
                }
        );

        response.getBody();
        if (response.getBody().isEmpty()) {
            throw new RuntimeException("User not found in Keycloak");
        }

        // Extract user ID
        Map<String, Object> user = response.getBody().get(0);
        String userId = (String) user.get("id");

        // Set password
        String passwordUrl = serverUrl + ADMIN + REALMS + realm + "/users/" + userId + "/reset-password";

        Map<String, Object> passwordPayload = new HashMap<>();
        passwordPayload.put("type", PASSWORD);
        passwordPayload.put("value", password);
        passwordPayload.put("temporary", false);

        HttpEntity<Map<String, Object>> passwordEntity = new HttpEntity<>(passwordPayload, headers);

        restTemplate.exchange(passwordUrl, HttpMethod.PUT, passwordEntity, String.class);

        log.info("Password set successfully for user: {}", username);

    }



    public String getAdminToken() {

        String keycloakUrl = serverUrl + REALMS + realm + PROTOCOL_OPEN_ID_CONNECT_TOKEN;

        // Define request parameters
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE, CLIENT_CREDENTIALS);
        body.add(CLIENT_ID, clientId);
        body.add(CLIENT_SECRET, clientSecret);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.exchange(
                keycloakUrl,
                HttpMethod.POST,
                request,
                Map.class
        );

        // Extract access token
        return Objects.requireNonNull(response.getBody()).get(ACCESS_TOKEN).toString();
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

        if (userDtoRq.getDepartementNom() != null && !userDtoRq.getDepartementNom().isEmpty()) {
            Departement departement = departementRepository.findByNom(userDtoRq.getDepartementNom())
                    .orElseThrow(() -> new RuntimeException("Département introuvable avec le nom : " + userDtoRq.getDepartementNom()));
            utilisateur.setDepartement(departement);
        } else {
            utilisateur.setDepartement(null);
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
