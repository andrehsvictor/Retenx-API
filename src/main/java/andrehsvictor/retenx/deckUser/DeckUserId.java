package andrehsvictor.retenx.deckUser;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@EqualsAndHashCode(of = { "deckId", "userId" })
public class DeckUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deckId;
    private Long userId;

}
