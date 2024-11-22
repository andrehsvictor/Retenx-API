package andrehsvictor.retenx.deck;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import andrehsvictor.retenx.deckUser.DeckUser;
import andrehsvictor.retenx.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "decks")
@EqualsAndHashCode(of = "id")
@ToString(exclude = { "author" })
public class Deck implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @ManyToOne
    @JoinTable(name = "author_id")
    private User author;

    private String hexColor;

    @Builder.Default
    @OneToMany(mappedBy = "deck")
    private Set<DeckUser> users = new HashSet<>();

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private DeckVisibility visibility = DeckVisibility.PRIVATE;

    @Builder.Default
    private Integer usersCount = 1;

    @Builder.Default
    private Integer cardsCount = 0;

    @Builder.Default
    private Integer likesCount = 0;

    @Builder.Default
    private Integer viewsCount = 0;

    private String imageUrl;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();

    private LocalDateTime publishedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
