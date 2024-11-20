package andrehsvictor.retenx.user;

import java.net.URI;
import java.util.Map;

import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import andrehsvictor.retenx.keycloak.KeycloakMapper;
import andrehsvictor.retenx.keycloak.KeycloakUserService;
import andrehsvictor.retenx.user.dto.EmailDto;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;
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
        userRepresentation = keycloakUserService.register(userRepresentation);
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

    @PutMapping("/api/v1/users/me")
    public ResponseEntity<GetMeDto> updateMe(@RequestBody @Valid PutUserDto putUserDto) {
        User user = userService.findOrCreateAuthenticatedUser();
        UserRepresentation userRepresentation = keycloakUserService.findById(user.getExternalId());

        userRepresentation = keycloakMapper.updateFromPutUserDto(putUserDto, userRepresentation);
        user = userMapper.updateFromPutUserDto(putUserDto, user);

        userRepresentation = keycloakUserService.update(user.getExternalId(), userRepresentation);
        user = userService.save(user);

        GetMeDto getMeDto = userMapper.toGetMeDto(user, userRepresentation.isEmailVerified());
        return ResponseEntity.ok(getMeDto);
    }

    @DeleteMapping("/api/v1/users/me")
    public ResponseEntity<Void> deleteMe() {
        User user = userService.findOrCreateAuthenticatedUser();
        keycloakUserService.deleteById(user.getExternalId());
        userService.deleteById(user.getId());
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/v1/users/password")
    public ResponseEntity<Map<String, String>> sendUpdatePasswordEmail(@RequestBody @Valid EmailDto emailDto) {
        keycloakUserService.sendUpdatePasswordEmail(emailDto.getEmail());
        Map<String, String> response = Map.of("message", "An e-mail was sent to update your password.");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/api/v1/users/verify-email")
    public ResponseEntity<Map<String, String>> sendVerifyEmail(@RequestBody @Valid EmailDto emailDto) {
        keycloakUserService.sendVerifyEmail(emailDto.getEmail());
        Map<String, String> response = Map.of("message", "A verify e-mail was sent.");
        return ResponseEntity.ok(response);
    }
}
