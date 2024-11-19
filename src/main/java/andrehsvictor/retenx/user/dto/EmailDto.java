package andrehsvictor.retenx.user.dto;

import andrehsvictor.retenx.user.validation.UnverifiedEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailDto {

    @UnverifiedEmail
    @Email(message = "E-mail must be valid.")
    @NotBlank(message = "E-mail is required.")
    @Size(min = 5, max = 255, message = "E-mail must be between 5 and 255 characters.")
    private String email;

}
