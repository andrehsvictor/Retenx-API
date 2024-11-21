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
        return keycloakUserRepository.save(keycloakUser);
    }

    public void delete(KeycloakUser keycloakUser) {
        keycloakUserRepository.delete(keycloakUser);
    }

    public void deleteById(String id) {
        keycloakUserRepository.deleteById(id);
    }

    public KeycloakUser findById(String id) {
        return keycloakUserRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND,
                        "User not found with external ID: " + id + "."));
    }

    public boolean existsById(String id) {
        return keycloakUserRepository.existsById(id);
    }

    public KeycloakUser findByUsername(String username) {
        return keycloakUserRepository.findByUsername(username)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND,
                        "User not found with username: " + username + "."));
    }

    public KeycloakUser findByEmail(String email) {
        return keycloakUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with e-mail: " + email + "."));
    }

    public boolean isEmailVerified(String email) {
        return findByEmail(email).isEmailVerified();
    }

    public void sendVerifyEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        if (keycloakUser.isEmailVerified()) {
            throw new RetenxException(HttpStatus.BAD_REQUEST, "E-mail already verified.");
        }
        keycloakUser.sendVerifyEmail();
    }

    public void sendUpdatePasswordEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        keycloakUser.executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }
}
