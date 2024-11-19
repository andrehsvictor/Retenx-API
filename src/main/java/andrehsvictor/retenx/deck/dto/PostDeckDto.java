package andrehsvictor.retenx.deck.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDeckDto {

    @Size(min = 3, max = 50)
    @NotBlank(message = "Name is required.")
    private String name;

    @Size(max = 255)
    private String description;

    @Pattern(message = "Visibility must be PUBLIC or PRIVATE.", regexp = "PUBLIC|PRIVATE")
    private String visibility;

    @Pattern(message = "Image URL must be a valid URL.", regexp = "^(http|https)://.*$")
    private String imageUrl;
}
