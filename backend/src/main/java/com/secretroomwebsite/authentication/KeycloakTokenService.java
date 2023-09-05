package com.secretroomwebsite.authentication;

import com.secretroomwebsite.exception.KeycloakAuthenticationException;
import com.secretroomwebsite.exception.KeycloakCommunicationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

@Service
public class KeycloakTokenService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.user-realm}")
    private String userRealm;

    @Value("${keycloak.webclient-id}")
    private String webclientId;


    public KeycloakTokenService() {}


    public KeycloakTokenResponse fetchAccessToken(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", webclientId);
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);

        KeycloakTokenResponse response;
        try {
            response = WebClient.builder()
                    .baseUrl(serverUrl)
                    .build()
                    .post()
                    .uri("/realms/" + userRealm + "/protocol/openid-connect/token")
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                    .body(BodyInserters.fromFormData(map))
                    .retrieve()
                    .onStatus(httpStatus -> httpStatus.value() == 401, clientResponse ->
                            Mono.error(new KeycloakAuthenticationException("Unknown username or password")))
                    .bodyToMono(KeycloakTokenResponse.class)
                    .block();
        } catch (ResponseStatusException e) {
            throw new KeycloakCommunicationException("Error communicating with Keycloak server");
        }

        return response;
    }
}
