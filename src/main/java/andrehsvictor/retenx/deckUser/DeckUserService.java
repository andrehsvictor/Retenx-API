package andrehsvictor.retenx.deckUser;

import org.springframework.stereotype.Service;

import andrehsvictor.retenx.deck.Deck;
import andrehsvictor.retenx.user.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeckUserService {

    private final DeckUserRepository deckUserRepository;
    private final DeckUserFactory deckUserFactory;

    public DeckUser create(Deck deck, User user, DeckUserRole role) {
        DeckUser deckUser = deckUserFactory.create(deck, user, role);
        return deckUserRepository.save(deckUser);
    }
}
