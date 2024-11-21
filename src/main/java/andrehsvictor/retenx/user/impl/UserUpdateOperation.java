package andrehsvictor.retenx.user.impl;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.keycloak.user.KeycloakUserMapper;
import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.User;
import andrehsvictor.retenx.user.UserRepository;
import andrehsvictor.retenx.user.UserSavingOperation;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserUpdateOperation implements UserSavingOperation {

    private final UserRepository userRepository;
    private final KeycloakUserMapper keycloakUserMapper;
    private final KeycloakUserService keycloakUserService;

    @Override
    public User save(User user) {
        KeycloakUser keycloakUser = keycloakUserMapper.updateKeycloakUserFromUser(user);
        keycloakUser = keycloakUserService.save(keycloakUser);
        user = userRepository.save(user);
        user.setEmailVerified(keycloakUser.isEmailVerified());
        return user;
    }

}
