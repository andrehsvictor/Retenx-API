package andrehsvictor.retenx.deckUser;

import java.io.Serializable;
import java.time.LocalDateTime;

import andrehsvictor.retenx.deck.Deck;
import andrehsvictor.retenx.user.User;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "decks_users")
@EqualsAndHashCode(of = "id")
public class DeckUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private DeckUserId id;

    @ManyToOne
    @MapsId("deckId")
    private Deck deck;

    @ManyToOne
    @MapsId("userId")
    private User user;

    @Builder.Default
    private DeckUserRole role = DeckUserRole.USER;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
