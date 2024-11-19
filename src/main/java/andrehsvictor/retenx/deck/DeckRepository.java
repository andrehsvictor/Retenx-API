package andrehsvictor.retenx.deck;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findByIdAndUsersUserIdOrVisibility(Long deckId, Long userId, DeckVisibility visibility);

    Page<Deck> findByUsersUserId(Long userId, Pageable pageable);

    Page<Deck> findAllByVisibility(DeckVisibility visibility, Pageable pageable);
    
}
