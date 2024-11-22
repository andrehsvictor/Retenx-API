package andrehsvictor.retenx.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import andrehsvictor.retenx.user.operation.UserOperationFactory;
import andrehsvictor.retenx.user.operation.UserOperationInput;
import andrehsvictor.retenx.user.operation.UserOperationOutput;
import andrehsvictor.retenx.user.operation.UserOperationType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserOperationFactory userOperationFactory;

    public User save(User user) {
        UserOperationInput input = new UserOperationInput(user);
        if (user.getId() == null) {
            UserOperationOutput output = userOperationFactory.get(UserOperationType.CREATE).execute(input);
            return output.getUser();
        }
        UserOperationOutput output = userOperationFactory.get(UserOperationType.UPDATE).execute(input);
        return output.getUser();
    }

    public boolean existsByExternalId(String externalId) {
        return userRepository.existsByExternalId(externalId);
    }

    public void delete(User user) {
        UserOperationInput input = new UserOperationInput(user);
        userOperationFactory.get(UserOperationType.DELETE).execute(input);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with ID: " + id + "."));
    }

    public User findByExternalId(String externalId) {
        UserOperationInput input = new UserOperationInput(externalId);
        UserOperationOutput output = userOperationFactory.get(UserOperationType.FIND_BY_EXTERNAL_ID).execute(input);
        return output.getUser();
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
