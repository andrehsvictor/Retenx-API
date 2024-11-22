package andrehsvictor.retenx.user.operation;

import andrehsvictor.retenx.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserOperationInput {
    private Long id;
    private String externalId;
    private User user;

    public UserOperationInput(Long id) {
        this.id = id;
    }

    public UserOperationInput(String externalId) {
        this.externalId = externalId;
    }

    public UserOperationInput(User user) {
        this.user = user;
    }
}
