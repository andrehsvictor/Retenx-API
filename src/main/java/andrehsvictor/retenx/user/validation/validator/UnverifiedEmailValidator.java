package andrehsvictor.retenx.user.validation.validator;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.user.UserService;
import andrehsvictor.retenx.user.validation.UnverifiedEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnverifiedEmailValidator implements ConstraintValidator<UnverifiedEmail, String> {

    private final UserService userService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userService.isEmailVerified(email);
    }

}
