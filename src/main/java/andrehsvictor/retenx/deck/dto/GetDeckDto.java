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
    private String visibility;
    private String imageUrl;
    private GetUserDto author;
    private String hexColor;
    private String createdAt;
    private String updatedAt;
    private Integer usersCount;
    private Integer cardsCount;
    private Integer likesCount;
    private Integer viewsCount;
    private String publishedAt;
}
