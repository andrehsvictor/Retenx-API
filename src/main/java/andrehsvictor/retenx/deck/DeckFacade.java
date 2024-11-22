package andrehsvictor.retenx.deck;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeckFacade {

    private final DeckService deckService;
    private final DeckMapper deckMapper;
    
}
