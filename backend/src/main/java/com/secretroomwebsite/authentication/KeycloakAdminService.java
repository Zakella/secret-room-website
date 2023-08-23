package com.secretroomwebsite.authentication;

import jakarta.ws.rs.client.ClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

@Service
public class KeycloakAdminService {


    @Value("${keycloak.server-url}")
    private String serverUrl;

    @Value("${keycloak.realm}")
    private String realm;
    //
    @Value("${keycloak.client-id}")
    private String clientId;
    //
    @Value("${keycloak.admin-user}")
    private String adminUser;

    @Value("${keycloak.admin-password}")
    private String adminPassword;


    public KeycloakAdminService() {

    }

    public Keycloak getInstance() {

        return KeycloakBuilder.builder()
                .serverUrl(serverUrl)
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .username(adminUser)
                .password(adminPassword)
                .clientId(clientId)
                .build();
    }
}