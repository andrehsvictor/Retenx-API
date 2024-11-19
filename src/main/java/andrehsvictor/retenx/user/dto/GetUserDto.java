package andrehsvictor.retenx.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetUserDto {
    private Long id;
    private String fullName;
    private String username;
    private String avatarUrl;
    private String bio;
    private String createdAt;
}
