package andrehsvictor.retenx.user.operation.impl;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.keycloak.user.KeycloakUserMapper;
import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.User;
import andrehsvictor.retenx.user.UserRepository;
import andrehsvictor.retenx.user.operation.UserOperation;
import andrehsvictor.retenx.user.operation.UserOperationInput;
import andrehsvictor.retenx.user.operation.UserOperationOutput;
import andrehsvictor.retenx.user.operation.UserOperationType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component(UserOperationType.CREATE)
public class UserCreationOperation implements UserOperation {

    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;
    private final KeycloakUserMapper keycloakUserMapper;

    @Override
    public UserOperationOutput execute(UserOperationInput input) {
        User user = input.getUser();
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null.");
        }
        KeycloakUser keycloakUser = keycloakUserMapper.userToKeycloakUser(user);
        keycloakUser = keycloakUserService.save(keycloakUser);
        user.setExternalId(keycloakUser.getId());
        user = userRepository.save(user);
        user.setEmailVerified(keycloakUser.isEmailVerified());
        return UserOperationOutput.builder()
                .user(user)
                .build();
    }

}
