package andrehsvictor.retenx.user;

import java.net.URI;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import andrehsvictor.retenx.keycloak.KeycloakMapper;
import andrehsvictor.retenx.keycloak.KeycloakUserService;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final KeycloakUserService keycloakUserService;
    private final KeycloakMapper keycloakMapper;

    @PostMapping("/api/v1/users")
    public ResponseEntity<GetMeDto> create(@RequestBody @Valid PostUserDto postUserDto) {
        UserRepresentation userRepresentation = keycloakMapper.toUserRepresentation(postUserDto);
        userRepresentation = keycloakUserService.create(userRepresentation);
        User user = userMapper.toUser(postUserDto, userRepresentation.getId());
        user = userService.save(user);
        GetMeDto getMeDto = userMapper.toGetMeDto(user, userRepresentation.isEmailVerified());
        URI location = URI.create("/api/v1/users/" + user.getId());
        return ResponseEntity.created(location).body(getMeDto);
    }

    @GetMapping("/api/v1/users/me")
    public ResponseEntity<GetMeDto> getMe() {
        User user = userService.findOrCreateAuthenticatedUser();
        boolean emailVerified = keycloakUserService.isEmailVerified(user.getEmail());
        GetMeDto getMeDto = userMapper.toGetMeDto(user, emailVerified);
        return ResponseEntity.ok(getMeDto);
    }
}
