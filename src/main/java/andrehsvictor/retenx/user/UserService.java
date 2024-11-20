package andrehsvictor.retenx.user;

import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import andrehsvictor.retenx.exception.RetenxException;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Service("defaultUserService")
public class UserService {

    private final UserRepository userRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with ID: " + id + "."));
    }

    public User findByExternalId(String externalId) {
        return userRepository.findByExternalId(externalId)
                .orElseThrow(() -> new RetenxException(HttpStatus.NOT_FOUND,
                        "User not found with external ID: " + externalId + "."));
    }

    public boolean isEmailVerified(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'isEmailVerified'");
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(
                        () -> new RetenxException(HttpStatus.NOT_FOUND, "User not found with e-mail: " + email + "."));
    }

    public void sendVerifyEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'sendVerifyEmail'");
    }

    public void sendUpdatePasswordEmail(String email) {
        throw new UnsupportedOperationException("Unimplemented method 'sendUpdatePasswordEmail'");
    }

}
