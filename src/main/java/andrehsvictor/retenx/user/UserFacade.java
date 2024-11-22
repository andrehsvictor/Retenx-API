package andrehsvictor.retenx.user;

import java.util.Map;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.dto.EmailDto;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserFacade {
    private final UserService userService;
    private final KeycloakUserService keycloakUserService;
    private final UserMapper userMapper;

    public GetMeDto create(PostUserDto postUserDto) {
        User user = userMapper.postUserDtoToUser(postUserDto);
        user = userService.save(user);
        return userMapper.userToGetMeDto(user);
    }

    public GetMeDto update(String externalId, PutUserDto putUserDto) {
        User user = userService.findByExternalId(externalId);
        user = userMapper.updateUserFromPutUserDto(putUserDto, user);
        user = userService.save(user);
        return userMapper.userToGetMeDto(user);
    }

    public GetMeDto getMe(String externalId) {
        User user = userService.findByExternalId(externalId);
        return userMapper.userToGetMeDto(user);
    }

    public void delete(String externalId) {
        User user = userService.findByExternalId(externalId);
        userService.delete(user);
    }

    public Map<String, String> sendVerifyEmail(EmailDto emailDto) {
        keycloakUserService.sendVerifyEmail(emailDto.getEmail());
        return Map.of("message", "E-mail sent successfully.");
    }

    public Map<String, String> sendUpdatePasswordEmail(EmailDto emailDto) {
        keycloakUserService.sendUpdatePasswordEmail(emailDto.getEmail());
        return Map.of("message", "An e-mail with instructions to update your password has been sent.");
    }
}
