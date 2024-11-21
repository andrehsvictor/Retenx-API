package andrehsvictor.retenx.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserSavingOperationFactory userSavingOperationFactory;
    private final UserMapper userMapper;
    private final KeycloakUserService keycloakUserService;

    public User save(User user) {
        UserSavingOperation userSavingOperation;
        if (user.getId() == null) {
            userSavingOperation = userSavingOperationFactory.getOperation("create");
            return userSavingOperation.save(user);
        }
        userSavingOperation = userSavingOperationFactory.getOperation("update");
        return userSavingOperation.save(user);
    }

    public boolean existsByExternalId(String externalId) {
        return userRepository.existsByExternalId(externalId);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with ID: " + id + "."));
    }

    public User findOrCreateByExternalId(String externalId) {
        if (!keycloakUserService.existsById(externalId)) {
            throw new RetenxException(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByExternalId(externalId).orElseGet(() -> {
            KeycloakUser keycloakUser = keycloakUserService.findById(externalId);
            User newUser = userMapper.keycloakUserToUser(keycloakUser);
            return save(newUser);
        });
        boolean emailVerified = keycloakUserService.isEmailVerified(externalId);
        user.setEmailVerified(emailVerified);
        return user;
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
