package andrehsvictor.retenx.user.validation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import andrehsvictor.retenx.user.validation.validator.UnverifiedEmailValidator;
import jakarta.validation.Constraint;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UnverifiedEmailValidator.class)
public @interface UnverifiedEmail {

    String message() default "E-mail is already verified.";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
    
}
