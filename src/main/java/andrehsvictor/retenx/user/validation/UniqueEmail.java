package andrehsvictor.retenx.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import andrehsvictor.retenx.user.validation.validator.UniqueEmailValidator;
import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueEmailValidator.class)
public @interface UniqueEmail {
    String message() default "E-mail already exists.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
