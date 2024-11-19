package andrehsvictor.retenx.user.validation;

import andrehsvictor.retenx.user.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;

@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "E-mail already exists.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
