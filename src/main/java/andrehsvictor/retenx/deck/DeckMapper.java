package andrehsvictor.retenx.deck;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import andrehsvictor.retenx.deck.dto.GetDeckDto;
import andrehsvictor.retenx.deck.dto.PostDeckDto;
import andrehsvictor.retenx.user.User;
import andrehsvictor.retenx.user.UserMapper;

@Mapper(componentModel = ComponentModel.SPRING, imports = DeckVisibility.class, uses = UserMapper.class)
public interface DeckMapper {

    @Mapping(target = "visibility", defaultExpression = "java(DeckVisibility.PRIVATE)")
    Deck toDeck(PostDeckDto postDeckDto);

    GetDeckDto toGetDeckDto(Deck deck);
    
}
