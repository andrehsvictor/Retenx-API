package andrehsvictor.retenx.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import andrehsvictor.retenx.property.KeycloakProperties;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {

    private final KeycloakProperties keycloakProperties;

    @Bean
    Keycloak keycloak() {
        String username = keycloakProperties.getAdmin().get("username");
        String password = keycloakProperties.getAdmin().get("password");
        return KeycloakBuilder.builder()
                .serverUrl(keycloakProperties.getAuthServerUrl())
                .realm(keycloakProperties.getRealm())
                .clientId(keycloakProperties.getClientId())
                .clientSecret(keycloakProperties.getClientSecret())
                .username(username)
                .password(password)
                .grantType(OAuth2Constants.PASSWORD)
                .build();
    }

    @Bean
    RealmResource realmResource(Keycloak keycloak) {
        return keycloak.realm(keycloakProperties.getRealm());
    }

    @Bean
    UsersResource usersResource(RealmResource realmResource) {
        return realmResource.users();
    }
}
