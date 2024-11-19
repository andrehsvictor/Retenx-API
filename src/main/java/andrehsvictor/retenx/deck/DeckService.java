package andrehsvictor.retenx.deck;

import java.time.LocalDateTime;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeckService {

    private final DeckRepository deckRepository;

    @Cacheable(value = "deck", key = "#deckId")
    public Deck findByIdAndUserIdOrPublicVisibility(Long deckId, Long userId) {
        return deckRepository.findByIdAndUsersUserIdOrVisibility(deckId, userId, DeckVisibility.PUBLIC)
                .orElseThrow(
                        () -> new RetenxException(HttpStatus.NOT_FOUND, "Deck not found with ID: " + deckId + "."));
    }

    @Transactional
    @CachePut(value = "deck", key = "#result.id")
    public Deck create(Deck deck) {
        if (deck.getId() != null) {
            throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Deck ID must be null.");
        }
        deck.setHexColor(createRandomHexColor());
        if (deck.getVisibility().equals(DeckVisibility.PUBLIC)) {
            deck.setPublishedAt(LocalDateTime.now());
        }
        return deckRepository.save(deck);
    }

    @CachePut(value = "deck", key = "#deck.id")
    public void incrementViewsCount(Deck deck) {
        deck.setViewsCount(deck.getViewsCount() + 1);
        deckRepository.save(deck);
    }

    public String createRandomHexColor() {
        return String.format("#%06x", (int) (Math.random() * 0xffffff));
    }

    @Transactional
    @CacheEvict(value = "deck", key = "#deck.id")
    public void deleteByIdAndUserId(Long deckId, Long userId) {
        Deck deck = findByIdAndUserIdOrPublicVisibility(deckId, userId);
        if (!deck.getAuthor().getId().equals(userId)) {
            throw new RetenxException(HttpStatus.FORBIDDEN, "You are not the author of this deck.");
        }
        deckRepository.deleteById(deckId);
    }

    @Transactional
    @CachePut(value = "deck", key = "#result.id")
    public Deck update(Deck existingDeck, Deck updatedDeck) {
        if (!existingDeck.getId().equals(updatedDeck.getId())) {
            throw new RetenxException(HttpStatus.INTERNAL_SERVER_ERROR, "Deck ID must be the same.");
        }
        if (!existingDeck.getAuthor().getId().equals(updatedDeck.getAuthor().getId())) {
            throw new RetenxException(HttpStatus.FORBIDDEN, "You are not the author of this deck.");
        }
        return deckRepository.save(updatedDeck);
    }

    public Page<Deck> findAllByUserId(Long userId, Pageable pageable) {
        return deckRepository.findByUsersUserId(userId, pageable);
    }

    public Page<Deck> findAllByPublicVisibility(Pageable pageable) {
        return deckRepository.findAllByVisibility(DeckVisibility.PUBLIC, pageable);
    }

}
