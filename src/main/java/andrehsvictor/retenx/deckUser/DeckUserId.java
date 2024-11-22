package andrehsvictor.retenx.deckUser;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class DeckUserId implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long deckId;
    private Long userId;

}
