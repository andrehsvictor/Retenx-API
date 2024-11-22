package andrehsvictor.retenx.user.operation;

import java.util.Map;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserOperationFactory {

    private final Map<String, UserOperation> userOperations;

    public UserOperation get(String operationType) {
        return userOperations.get(operationType);
    }
}
