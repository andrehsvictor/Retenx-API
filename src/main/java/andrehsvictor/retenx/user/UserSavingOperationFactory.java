package andrehsvictor.retenx.user;

import java.util.Map;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserSavingOperationFactory {

    private final Map<String, UserSavingOperation> userSavingOperations;

    public UserSavingOperation getOperation(String operation) {
        operation = operation.toLowerCase().trim().replace(" ", "");
        switch (operation) {
            case "create":
                return userSavingOperations.get("userCreationOperation");
            case "update":
                return userSavingOperations.get("userUpdateOperation");
            default:
                throw new IllegalArgumentException("Invalid user saving operation: " + operation + ".");
        }
    }
}
