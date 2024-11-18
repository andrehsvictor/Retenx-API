package andrehsvictor.retenx.property;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

    @NotEmpty(message = "Keycloak admin credentials must be provided.")
    @Size(min = 2, max = 2, message = "Keycloak admin credentials must have username and password.")
    private Map<String, String> admin;
    
}
