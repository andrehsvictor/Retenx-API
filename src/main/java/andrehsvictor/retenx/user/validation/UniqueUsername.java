package andrehsvictor.retenx.user.validation;

import andrehsvictor.retenx.user.validation.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;

@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username already exists.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
