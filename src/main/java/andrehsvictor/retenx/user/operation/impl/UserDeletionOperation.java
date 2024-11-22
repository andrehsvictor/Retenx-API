package andrehsvictor.retenx.user.operation.impl;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.User;
import andrehsvictor.retenx.user.UserRepository;
import andrehsvictor.retenx.user.operation.UserOperation;
import andrehsvictor.retenx.user.operation.UserOperationInput;
import andrehsvictor.retenx.user.operation.UserOperationOutput;
import andrehsvictor.retenx.user.operation.UserOperationType;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component(UserOperationType.DELETE)
public class UserDeletionOperation implements UserOperation {

    private final UserRepository userRepository;
    private final KeycloakUserService keycloakUserService;

    @Override
    public UserOperationOutput execute(UserOperationInput input) {
        User user = input.getUser();
        userRepository.delete(user);
        keycloakUserService.deleteById(user.getExternalId());
        return UserOperationOutput.builder()
                .deleted(true)
                .build();
    }

}
