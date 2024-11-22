package andrehsvictor.retenx.deck.dto;

import andrehsvictor.retenx.user.dto.GetUserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetDeckDto {
    private Long id;
    private String name;
    private String description;
    private String hexColor;
    private GetUserDto author;
    private Integer usersCount;
    private Integer cardsCount;
    private Integer likesCount;
    private Integer viewsCount;
    private String visibility;
    private String createdAt;
    private String updatedAt;
    private String publishedAt;
}
