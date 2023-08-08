package com.secretroomwebsite.keycloack;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KeycloakTokenService {

    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.user-realm}")
    private String userRealm;


    public KeycloakTokenService() {}


    public KeycloakTokenResponse fetchAccessToken(String username, String password) {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("client_id", "test2");
        map.add("grant_type", "password");
        map.add("username", username);
        map.add("password", password);


        KeycloakTokenResponse response =  WebClient.builder()
                .baseUrl(serverUrl)
                .build()
                .post()
                .uri("/realms/" + userRealm + "/protocol/openid-connect/token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .body(BodyInserters.fromFormData(map))
                .retrieve()
                .bodyToMono(KeycloakTokenResponse.class)
                .block(); // Этот блок преобразует асинхронный Mono<String> в синхронный String

        // TODO: Обработка и анализ ответа (например, используя Jackson для преобразования JSON)
        return response;
    }
}
