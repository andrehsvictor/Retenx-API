package andrehsvictor.retenx.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import andrehsvictor.retenx.user.validation.validator.UniqueUsernameValidator;
import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueUsernameValidator.class)
public @interface UniqueUsername {
    String message() default "Username already exists.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
