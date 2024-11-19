package andrehsvictor.retenx.user.dto;

import andrehsvictor.retenx.user.validation.UniqueEmail;
import andrehsvictor.retenx.user.validation.UniqueUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostUserDto {

    @NotBlank(message = "First name is required.")
    @Size(min = 2, max = 50, message = "First name must be between 2 and 50 characters.")
    private String firstName;

    @NotBlank(message = "Last name is required.")
    @Size(min = 2, max = 100, message = "Last name must be between 2 and 100 characters.")
    private String lastName;

    @UniqueEmail
    @Email(message = "E-mail must be a valid e-mail address.")
    @NotBlank(message = "E-mail is required.")
    @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters.")
    private String email;

    @UniqueUsername
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters.")
    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "Password must contain at least one lowercase letter, one uppercase letter, and one digit.")
    private String password;

    @Size(max = 500, message = "Bio must be less than 500 characters.")
    private String bio;

    @Size(min = 5, max = 2000, message = "Avatar URL must be between 5 and 2000 characters.")
    @Pattern(regexp = "^(http|https)://.*$", message = "Avatar URL must be a valid URL.")
    private String avatarUrl;
}
