package andrehsvictor.retenx.user.validation.validator;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.user.KeycloakUserService;
import andrehsvictor.retenx.user.UserService;
import andrehsvictor.retenx.user.validation.UniqueEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {

    private final UserService userService;
    private final KeycloakUserService keycloakUserService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null) {
            return true;
        }
        return !userService.existsByEmail(email) || !keycloakUserService.existsByEmail(email);
    }

}
