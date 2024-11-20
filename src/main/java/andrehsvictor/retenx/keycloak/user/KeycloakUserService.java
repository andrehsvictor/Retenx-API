package andrehsvictor.retenx.keycloak.user;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final KeycloakUserRepository keycloakUserRepository;

    public KeycloakUser save(KeycloakUser keycloakUser) {
        keycloakUser.setEnabled(true);
        keycloakUser.setRequiredActions(List.of("VERIFY_EMAIL"));
        keycloakUser.setEmailVerified(false);
        return keycloakUserRepository.save(keycloakUser);
    }

    public KeycloakUser findByEmail(String email) {
        return keycloakUserRepository.findByEmail(email).orElseThrow(
                () -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with e-mail: " + email + "."));
    }

    public boolean isEmailVerified(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        return keycloakUser.isEmailVerified();
    }
}
