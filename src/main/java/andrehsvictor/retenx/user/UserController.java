package andrehsvictor.retenx.user;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import andrehsvictor.retenx.user.dto.GetMeDto;
import andrehsvictor.retenx.user.dto.PostUserDto;
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

    @GetMapping("/api/v1/users/me")
    public @ResponseBody GetMeDto getMe() {
        String externalId = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication()).getToken()
                .getSubject();
        return userFacade.getMe(externalId);
    }
}
