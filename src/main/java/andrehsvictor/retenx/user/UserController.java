package andrehsvictor.retenx.user;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import andrehsvictor.retenx.user.dto.EmailDto;
import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
import andrehsvictor.retenx.user.dto.PutUserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;

    @PostMapping("/api/v1/users")
    public ResponseEntity<GetMeDto> create(@RequestBody @Valid PostUserDto postUserDto) {
        GetMeDto getMeDto = userFacade.create(postUserDto);
        URI location = URI.create("/api/v1/users/" + getMeDto.getId());
        return ResponseEntity.created(location).body(getMeDto);
    }

    @DeleteMapping("/api/v1/users/me")
    public ResponseEntity<Void> delete(JwtAuthenticationToken jwt) {
        String externalId = jwt.getToken().getClaim("sub");
        userFacade.delete(externalId);
        SecurityContextHolder.clearContext();
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/api/v1/users/me")
    public ResponseEntity<GetMeDto> update(@RequestBody @Valid PutUserDto putUserDto, JwtAuthenticationToken jwt) {
        String externalId = jwt.getToken().getClaim("sub");
        GetMeDto getMeDto = userFacade.update(externalId, putUserDto);
        return ResponseEntity.ok(getMeDto);
    }

    @PostMapping("/api/v1/users/verify-email")
    public ResponseEntity<?> sendVerifyEmail(@RequestBody @Valid EmailDto emailDto) {
        return ResponseEntity.ok(userFacade.sendVerifyEmail(emailDto));
    }

    @PutMapping("/api/v1/users/password")
    public ResponseEntity<?> updatePassword(@RequestBody @Valid EmailDto emailDto) {
        return ResponseEntity.ok(userFacade.sendUpdatePasswordEmail(emailDto));
    }

    @GetMapping("/api/v1/users/me")
    public ResponseEntity<GetMeDto> getMe(JwtAuthenticationToken jwt) {
        String externalId = jwt.getToken().getClaim("sub");
        GetMeDto getMeDto = userFacade.getMe(externalId);
        return ResponseEntity.ok(getMeDto);
    }
}
