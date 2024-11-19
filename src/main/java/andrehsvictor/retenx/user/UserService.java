package andrehsvictor.retenx.user;

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
        String keycloakId = jwtAuthenticationToken.getToken().getSubject();
        if (!keycloakUserService.existsById(keycloakId)) {
            throw new RetenxException(HttpStatus.NOT_FOUND, "User not found in Keycloak with ID: " + keycloakId + ".");
        }
        return userRepository.findByKeycloakId(keycloakId).orElseGet(() -> {
            User.UserBuilder user = User.builder();
            user.keycloakId(keycloakId);
            user.username(jwtAuthenticationToken.getToken().getClaimAsString("preferred_username"));
            user.email(jwtAuthenticationToken.getToken().getClaimAsString("email"));
            user.firstName(jwtAuthenticationToken.getToken().getClaimAsString("given_name"));
            user.lastName(jwtAuthenticationToken.getToken().getClaimAsString("family_name"));
            user.avatarUrl(jwtAuthenticationToken.getToken().getClaimAsString("picture"));
            return userRepository.save(user.build());
        });
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with ID: " + id + "."));
    }

    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Transactional
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
