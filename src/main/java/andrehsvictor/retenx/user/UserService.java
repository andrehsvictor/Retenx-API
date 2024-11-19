package andrehsvictor.retenx.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import andrehsvictor.retenx.keycloak.KeycloakUserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;

    @Transactional
    public User findOrCreateAuthenticatedUser() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();
        String subject = jwtAuthenticationToken.getToken().getSubject();
        if (!keycloakUserService.existsById(subject)) {
            throw new RetenxException(HttpStatus.UNAUTHORIZED);
        }
        return userRepository.findByExternalId(subject).orElseGet(() -> {
            User.UserBuilder user = User.builder();
            user.externalId(subject);
            user.username(jwtAuthenticationToken.getToken().getClaimAsString("preferred_username"));
            user.email(jwtAuthenticationToken.getToken().getClaimAsString("email"));
            user.firstName(jwtAuthenticationToken.getToken().getClaimAsString("given_name"));
            user.lastName(jwtAuthenticationToken.getToken().getClaimAsString("family_name"));
            user.avatarUrl(jwtAuthenticationToken.getToken().getClaimAsString("picture"));
            return userRepository.save(user.build());
        });
    }

    @Cacheable(value = "user", key = "#id")
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with ID: " + id + "."));
    }

    @Transactional
    @CachePut(value = "user", key = "#result.id")
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(value = "user", key = "#id")
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
