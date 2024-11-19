package andrehsvictor.retenx.user.validation.validator;

import org.springframework.stereotype.Component;

import andrehsvictor.retenx.keycloak.KeycloakUserService;
import andrehsvictor.retenx.user.validation.UnverifiedEmail;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UnverifiedEmailValidator implements ConstraintValidator<UnverifiedEmail, String> {

    private final KeycloakUserService keycloakUserService;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !keycloakUserService.isEmailVerified(email);
    }

}
