package andrehsvictor.retenx.user.operation.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import andrehsvictor.retenx.exception.RetenxException;
import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.User;
import andrehsvictor.retenx.user.UserMapper;
import andrehsvictor.retenx.user.UserRepository;
import andrehsvictor.retenx.user.operation.UserOperation;
import andrehsvictor.retenx.user.operation.UserOperationInput;
import andrehsvictor.retenx.user.operation.UserOperationOutput;
import andrehsvictor.retenx.user.operation.UserOperationType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component(UserOperationType.FIND_BY_EXTERNAL_ID)
public class UserFindByExternalIdOperation implements UserOperation {

    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;
    private final UserMapper userMapper;

    @Override
    public UserOperationOutput execute(UserOperationInput input) {
        String externalId = input.getExternalId();
        if (!keycloakUserService.existsById(externalId)) {
            throw new RetenxException(HttpStatus.UNAUTHORIZED);
        }
        User user = userRepository.findByExternalId(externalId).orElseGet(() -> {
            KeycloakUser keycloakUser = keycloakUserService.findById(externalId);
            User newUser = userMapper.keycloakUserToUser(keycloakUser);
            return userRepository.save(newUser);
        });
        boolean emailVerified = keycloakUserService.isEmailVerified(user.getEmail());
        user.setEmailVerified(emailVerified);
        return new UserOperationOutput(user);
    }

}
