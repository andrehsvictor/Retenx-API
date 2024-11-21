package andrehsvictor.retenx.property;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Validated
public class KeycloakAdminCredentials {

    @NotBlank(message = "Keycloak admin username must be provided.")
    private String username;

    @NotBlank(message = "Keycloak admin password must be provided.")
    private String password;

}
