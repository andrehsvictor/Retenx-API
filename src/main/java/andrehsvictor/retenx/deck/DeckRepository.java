package andrehsvictor.retenx.deck;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import jakarta.transaction.Transactional;

public interface DeckRepository extends JpaRepository<Deck, Long> {

    Optional<Deck> findByIdAndUsersUserIdOrVisibility(Long id, Long userId, DeckVisibility visibility);

    Page<Deck> findAllByUsersUserId(Long userId, Pageable pageable);

    @Modifying
    @Transactional
    void deleteByIdAndAuthorId(Long id, Long authorId);

}
