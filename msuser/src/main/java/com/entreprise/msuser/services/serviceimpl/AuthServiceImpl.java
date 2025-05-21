package com.entreprise.msuser.services.serviceimpl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.JWT;
import com.entreprise.msuser.configuration.KeycloakConfig;
import com.entreprise.msuser.dtos.*;
import com.entreprise.msuser.feign.KeycloakClient;
import com.entreprise.msuser.mappers.KeycloakUserMapper;
import org.keycloak.representations.idm.RoleRepresentation;
import com.entreprise.msuser.services.AuthInterface;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.AccessTokenResponse;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import static com.entreprise.msuser.constants.Constants.*;

import java.security.Principal;
import java.util.*;


@Service
@Slf4j
public class AuthServiceImpl implements AuthInterface {

    private final KeycloakClient keycloakClient;
    private final KeycloakConfig keycloakConfig;
    private Keycloak keycloak;
    private final RestTemplate restTemplate;
    private final KeycloakUserMapper keycloakUserMapper ;


    public AuthServiceImpl(KeycloakClient keycloakClient,
                           KeycloakConfig keycloakConfig,
                           Keycloak keycloak,
                           RestTemplate restTemplate,
                           KeycloakUserMapper keycloakUserMapper) {
        this.keycloakClient = keycloakClient;
        this.keycloakConfig = keycloakConfig;
        this.keycloak = keycloak;
        this.restTemplate = restTemplate;
        this.keycloakUserMapper=keycloakUserMapper;
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
    public TokenDto login(LoginDto loginDto) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add(GRANT_TYPE, PASSWORD);
        map.add(USERNAME, loginDto.username());
        map.add(PASSWORD, loginDto.password());
        map.add(CLIENT_ID, clientId);
        map.add(CLIENT_SECRET, clientSecret);
        ResponseEntity<AccessTokenResponse> accessToken = keycloakClient.login(map);
        if (Objects.isNull(accessToken)) {
            throw new RuntimeException();
        } else {
            accessToken.getBody();
        }

        return TokenDto.builder()
                .accessToken(Objects.requireNonNull(accessToken.getBody()).getToken())
                .expIn(accessToken.getBody().getExpiresIn())
                .refExpIn(accessToken.getBody().getRefreshExpiresIn())
                .refreshToken(accessToken.getBody().getRefreshToken())
                .refreshExpiresIn(accessToken.getBody().getRefreshExpiresIn())
                .tokenType(accessToken.getBody().getTokenType())
                .build();
    }

    @Override
    public void logout(refreshTokenDto refreshToken) {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add(GRANT_TYPE, REFRESH_TOKEN);
        requestParams.add(CLIENT_ID, clientId);
        requestParams.add(CLIENT_SECRET, clientSecret);
        requestParams.add(REFRESH_TOKEN, refreshToken.getRefreshToken());

        keycloakClient.logout(realm, requestParams);
    }

    @Override
    public Map refreshAccessToken(refreshTokenDto refreshToken) {
        String url = String.format("%s/realms/%s/protocol/openid-connect/token", serverUrl, realm);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add(GRANT_TYPE, REFRESH_TOKEN);
        params.add(CLIENT_ID, clientId);
        params.add(CLIENT_SECRET, clientSecret);
        params.add(REFRESH_TOKEN, refreshToken.getRefreshToken());

        HttpHeaders headers = createFormUrlEncodedHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, request, Map.class);

        return response.getBody();
    }

    @Override
    public ResponseEntity<String> resetPassword(ResetPassword request,
                                                Principal principal) {

        if (principal == null) {
            throw new IllegalStateException("User is not authenticated");
        }
        String username = principal.getName();


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add(GRANT_TYPE, PASSWORD);
        body.add(CLIENT_ID, clientId);

        body.add(USERNAME, username);
        body.add(PASSWORD, request.getOldPassword());

        HttpEntity<MultiValueMap<String, String>> tokenRequest = new HttpEntity<>(body, headers);
        String tokenUrl = serverUrl + REALMS + realm + PROTOCOL_OPEN_ID_CONNECT_TOKEN;

        try {
            restTemplate.postForEntity(tokenUrl, tokenRequest, Map.class);
        } catch (HttpClientErrorException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Old password is incorrect");
        }


        List<UserRepresentation> users = keycloak.realm(realm).users().search(username);
        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }

        String userId = users.get(0).getId();
        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(request.getNewPassword());
        credential.setTemporary(false);

        keycloak.realm(realm).users().get(userId).resetPassword(credential);
        return ResponseEntity.ok("Password changed successfully");
    }


    @Override
    public ResponseEntity<String> forgotPassword(String email) {
        try {
            String adminToken = getAdminToken();
            String userId = getUserIdByEmail(email, adminToken);

            if (userId == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
            }

            String resetUrl = serverUrl + ADMIN + REALMS + realm + "/users/" + userId + "/execute-actions-email";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set(AUTHORIZATION, BEARER + adminToken);

            List<String> actions = Collections.singletonList("UPDATE_PASSWORD");
            HttpEntity<List<String>> entity = new HttpEntity<>(actions, headers);

            ResponseEntity<String> response = restTemplate.exchange(resetUrl, HttpMethod.PUT, entity, String.class);

            return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending password reset email.");
        }
    }

    private String getUserIdByEmail(String email, String adminToken) {
        String usersUrl = serverUrl + ADMIN + REALMS + realm + "/users?email=" + email;

        HttpHeaders headers = new HttpHeaders();
        headers.set(AUTHORIZATION, BEARER + adminToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<List> response = restTemplate.exchange(usersUrl, HttpMethod.GET, entity, List.class);

        response.getBody();
        if (!response.getBody().isEmpty()) {
            Map user = (Map) response.getBody().get(0);
            return user.get("id").toString();
        }

        return null;
    }

    public String getAdminToken() {

        String keycloakUrl = serverUrl + REALMS + realm + PROTOCOL_OPEN_ID_CONNECT_TOKEN;

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

        return response.getBody().get(ACCESS_TOKEN).toString();
    }


    public List<String> getUserRoles(String accessToken) {
        try {
            DecodedJWT jwt = JWT.decode(accessToken);
            Map<String, Object> realmAccess = jwt.getClaim("realm_access").asMap();

            if (realmAccess != null && realmAccess.containsKey("roles")) {
                List<String> roles = (List<String>) realmAccess.get("roles");
                return roles;
            }

            return Collections.emptyList();
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }






    @Override
    public ResponseEntity<List<UserDtoRsKey>> getKeycloakUsers() {
        List<UserRepresentation> users = keycloakConfig.keycloak()
                .realms()
                .realm(realm)
                .users()
                .list();

        List<UserDtoRsKey> userDtoskey = users.stream()
                .map(user -> {
                    // Retrieve roles of the user
                    List<RoleRepresentation> realmRoles = keycloakConfig.keycloak()
                            .realms()
                            .realm(realm)
                            .users()
                            .get(user.getId())
                            .roles()
                            .realmLevel()
                            .listEffective();

                    List<String> roleNames = realmRoles.stream()
                            .map(RoleRepresentation::getName)
                            .toList();

                    // Safe access to department attribute
                    String department = "";
                    if (user.getAttributes() != null && user.getAttributes().containsKey("department")) {
                        department = user.getAttributes().get("department").get(0);
                    }

                    return UserDtoRsKey.builder()
                            .username(user.getUsername())
                            .firstName(user.getFirstName())
                            .lastName(user.getLastName())
                            .email(user.getEmail())
                            .department(department)
                            .roles(roleNames)
                            .build();
                })
                .toList();

        return ResponseEntity.ok(userDtoskey);
    }





}


