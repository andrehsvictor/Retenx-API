package andrehsvictor.retenx.user.validation;

import andrehsvictor.retenx.user.validation.validator.UnverifiedEmailValidator;
import jakarta.validation.Constraint;

@Constraint(validatedBy = UnverifiedEmailValidator.class)
public @interface UnverifiedEmail {

    String message() default "E-mail is already verified.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
    
}
