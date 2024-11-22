package andrehsvictor.retenx.keycloak.user;

import java.util.List;

import org.keycloak.admin.client.resource.UsersResource;
import org.springframework.cache.CacheManager;
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
    private final CacheManager cacheManager;
    private final UsersResource usersResource;

    @Caching(evict = {
            @CacheEvict(value = "exists", key = "#keycloakUser.username"),
            @CacheEvict(value = "exists", key = "#keycloakUser.email"),
    }, put = {
            @CachePut(value = "keycloakUser", key = "{ #result.id, #result.username, #result.email }"),
    })
    public KeycloakUser save(KeycloakUser keycloakUser) {
        KeycloakUser savedKeycloakUser = keycloakUserRepository.save(keycloakUser);
        cacheManager.getCache("exists").put(savedKeycloakUser.getUsername(), true);
        cacheManager.getCache("exists").put(savedKeycloakUser.getEmail(), true);
        return savedKeycloakUser;
    }

    @Caching(evict = {
            @CacheEvict(value = "keycloakUser", key = "#id"),
            @CacheEvict(value = "exists", key = "#id"),
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

    @Cacheable(value = "exists", key = "#id")
    public boolean existsById(String id) {
        return keycloakUserRepository.existsById(id);
    }

    @Cacheable(value = "exists", key = "#username")
    public boolean existsByUsername(String username) {
        return keycloakUserRepository.existsByUsername(username);
    }

    @Cacheable(value = "exists", key = "#email")
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

    @Cacheable(value = "emailVerified", key = "#email")
    public boolean isEmailVerified(String email) {
        return findByEmail(email).isEmailVerified();
    }

    @Caching(evict = {
            @CacheEvict(value = "emailVerified", key = "#email"),
            @CacheEvict(value = "keycloakUser", key = "#email")
    })
    public void sendVerifyEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        if (keycloakUser.isEmailVerified()) {
            throw new RetenxException(HttpStatus.BAD_REQUEST, "E-mail already verified.");
        }
        usersResource.get(keycloakUser.getId()).sendVerifyEmail();
        cacheManager.getCache("keycloakUser").evictIfPresent(keycloakUser.getId());
        cacheManager.getCache("keycloakUser").evictIfPresent(keycloakUser.getUsername());
    }

    public void sendUpdatePasswordEmail(String email) {
        KeycloakUser keycloakUser = findByEmail(email);
        usersResource.get(keycloakUser.getId()).executeActionsEmail(List.of("UPDATE_PASSWORD"));
    }
}
