package andrehsvictor.retenx.keycloak.user;

import java.util.List;

import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakUserService {

    private final KeycloakUserRepository keycloakUserRepository;
    private final UsersResource usersResource;

    @CachePut(value = "keycloakUser", key = "{ #result.id, #result.username, #result.email }")
    public KeycloakUser save(KeycloakUser keycloakUser) {
        return keycloakUserRepository.save(keycloakUser);
    }

    @Caching(evict = {
            @CacheEvict(value = "keycloakUser", key = "#id"),
            @CacheEvict(value = "keycloakUser", key = "findById(#id).email"),
            @CacheEvict(value = "keycloakUser", key = "findById(#id).username")
    })
    public void deleteById(String id) {
        keycloakUserRepository.deleteById(id);
    }

    @Cacheable(value = "keycloakUser", key = "#id")
    public KeycloakUser findById(String id) {
        return keycloakUserRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND,
                        "User not found with external ID: " + id + "."));
    }

    public boolean existsById(String id) {
        return keycloakUserRepository.existsById(id);
    }

    public boolean existsByUsername(String username) {
        return keycloakUserRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return keycloakUserRepository.existsByEmail(email);
    }

    @Cacheable(value = "keycloakUser", key = "#username")
    public KeycloakUser findByUsername(String username) {
        return keycloakUserRepository.findByUsername(username)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND,
                        "User not found with username: " + username + "."));
    }

    @Cacheable(value = "keycloakUser", key = "#email")
    public KeycloakUser findByEmail(String email) {
        return keycloakUserRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with e-mail: " + email + "."));
    }

    public boolean isEmailVerified(String email) {
        return findByEmail(email).isEmailVerified();
    }

    @CacheEvict(value = "keycloakUser", key = "{ #email }")
    public void sendVerifyEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        if (keycloakUser.isEmailVerified()) {
            throw new RetenxException(HttpStatus.BAD_REQUEST, "E-mail already verified.");
        }
        usersResource.get(keycloakUser.getId()).sendVerifyEmail();
    }

    public void sendUpdatePasswordEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        usersResource.get(keycloakUser.getId()).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }
}
