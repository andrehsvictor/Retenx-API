package andrehsvictor.retenx.deck;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    public Deck save(Deck deck) {
        return deckRepository.save(deck);
    }

    public Deck findByIdAndUserId(Long id, Long userId) {
        return deckRepository.findByIdAndUsersUserIdOrVisibility(id, userId, DeckVisibility.PUBLIC)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + id + "."));
    }

    public Page<Deck> findAllByUserId(Long userId, Pageable pageable) {
        return deckRepository.findAllByUsersUserId(userId, pageable);
    }

    public void deleteByIdAndUserId(Long id, Long userId) {
        deckRepository.deleteByIdAndAuthorId(id, userId);
    }

}
