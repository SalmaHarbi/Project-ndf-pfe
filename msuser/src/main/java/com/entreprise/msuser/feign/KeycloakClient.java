package com.entreprise.msuser.feign;

import org.keycloak.representations.AccessTokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "keycloak-client", url = "http://localhost:8080")
public interface KeycloakClient {


    @PostMapping(
            value = "/realms/pfe-realm/protocol/openid-connect/token",
            consumes = "application/x-www-form-urlencoded")
    ResponseEntity<AccessTokenResponse> login(@RequestBody Map<String, ?> form);



    @PostMapping(
            value = "/realms/{realm}/protocol/openid-connect/logout",
            consumes = "application/x-www-form-urlencoded")
    ResponseEntity<Void> logout(@PathVariable String realm, @RequestBody Map<String, ?> form);

}
