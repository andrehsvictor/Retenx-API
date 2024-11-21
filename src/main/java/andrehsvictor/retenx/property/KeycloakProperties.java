package andrehsvictor.retenx.property;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties(prefix = "retenx.keycloak")
public class KeycloakProperties {

    @NotBlank(message = "Keycloak realm must be provided.")
    private String realm;

    @NotBlank(message = "Keycloak auth server URL must be provided.")
    @Pattern(regexp = "^(http|https)://.*$", message = "Keycloak auth server URL must start with http:// or https://.")
    private String authServerUrl;

    @NotBlank(message = "Keycloak client ID must be provided.")
    private String clientId;

    @NotBlank(message = "Keycloak client secret must be provided.")
    private String clientSecret;

    @NotNull(message = "Keycloak admin properties must be provided.")
    private KeycloakAdminCredentials admin;

}
