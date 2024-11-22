package andrehsvictor.retenx.deck;

import org.mapstruct.Mapper;

import andrehsvictor.retenx.deck.dto.GetDeckDto;
import andrehsvictor.retenx.user.UserMapper;

@Mapper(componentModel = "spring", uses = { UserMapper.class })
public interface DeckMapper {

    GetDeckDto deckToGetDeckDto(Deck deck);
    
}
