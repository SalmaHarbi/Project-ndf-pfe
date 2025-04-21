package com.entreprise.msuser.services.serviceimpl;

import com.entreprise.msuser.configuration.KeycloakConfig;
import com.entreprise.msuser.dtos.LoginDto;
import com.entreprise.msuser.dtos.TokenDto;
import com.entreprise.msuser.dtos.refreshTokenDto;
import com.entreprise.msuser.feign.KeycloakClient;
import com.entreprise.msuser.services.AuthInterface;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.AccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static com.entreprise.msuser.constants.Constants.*;

import java.util.Objects;



@Service
@Slf4j
public class AuthServiceImpl implements AuthInterface {

    private final KeycloakClient keycloakClient;
    private final KeycloakConfig keycloakConfig;
    private Keycloak keycloak;


    public AuthServiceImpl(KeycloakClient keycloakClient,
                           KeycloakConfig keycloakConfig,
                           Keycloak keycloak){
        this.keycloakClient=keycloakClient;
        this.keycloakConfig=keycloakConfig;
        this.keycloak=keycloak;
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
    public void logout(refreshTokenDto refreshTokenDto) {
        keycloakClient.logout(
                realm,
                clientId,
                clientSecret,
                refreshTokenDto.getRefreshToken()
        );
    }}