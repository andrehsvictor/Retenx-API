package andrehsvictor.retenx.user.operation;

import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserOperationFactory {

    private final Map<String, UserOperation> userOperations;

    public UserOperation get(String operationType) {
        Optional<UserOperation> userOperation = Optional.ofNullable(userOperations.get(operationType));
        return userOperation
                .orElseThrow(() -> new IllegalArgumentException("Invalid operation type: " + operationType));
    }
}
