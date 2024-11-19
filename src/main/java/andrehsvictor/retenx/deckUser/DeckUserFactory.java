package andrehsvictor.retenx.deckUser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants.ComponentModel;

import andrehsvictor.retenx.deck.Deck;
import andrehsvictor.retenx.user.User;

@Mapper(componentModel = ComponentModel.SPRING)
public interface DeckUserFactory {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    DeckUser create(Deck deck, User user, DeckUserRole role);

}
