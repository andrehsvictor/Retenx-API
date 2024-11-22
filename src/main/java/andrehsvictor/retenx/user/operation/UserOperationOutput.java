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
public class UserOperationOutput {
    private User user;
    private boolean deleted;

    public UserOperationOutput(User user) {
        this.user = user;
    }

    public UserOperationOutput(boolean deleted) {
        this.deleted = deleted;
    }
}
