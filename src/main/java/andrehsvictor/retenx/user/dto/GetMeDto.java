package andrehsvictor.retenx.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMeDto {
    private Long id;
    private String username;
    private String keycloakId;
    private String email;
    private boolean emailVerified;
    private String firstName;
    private String lastName;
    private String avatarUrl;
    private String bio;
    private String createdAt;
    private String updatedAt;
}
