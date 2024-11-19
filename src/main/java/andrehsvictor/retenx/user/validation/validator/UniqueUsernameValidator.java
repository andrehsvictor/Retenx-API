package andrehsvictor.retenx.user.validation.validator;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.user.UserService;
import andrehsvictor.retenx.user.validation.UniqueUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return true;
        }
        return !userService.existsByUsername(username);
    }

}
