package andrehsvictor.retenx.user;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUser;
import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFacade {

    private final UserMapper userMapper;
    private final UserService userService;
    private final KeycloakUserService keycloakUserService;

    public GetMeDto create(PostUserDto postUserDto) {
        User user = userMapper.postUserDtoToUser(postUserDto);
        KeycloakUser keycloakUser = userMapper.userToKeycloakUser(user);
        keycloakUser = keycloakUserService.save(keycloakUser);
        user = userMapper.keycloakUserToUser(keycloakUser);
        user = userService.save(user);
        return userMapper.userToGetMeDto(user, keycloakUser.isEmailVerified());
    }

    public GetMeDto getMe(String externalId) {
        User user = userService.findByExternalId(externalId);
        boolean emailVerified = keycloakUserService.isEmailVerified(user.getEmail());
        return userMapper.userToGetMeDto(user, emailVerified);
    }

}
