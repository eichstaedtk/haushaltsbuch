package de.eichstaedt.haushaltsbuch.application.validation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = BenutzernameValidator.class)
@Target({ METHOD, FIELD})
@Retention(RUNTIME)
public @interface BenutzernameAlreadyUsed {

  String message() default "Benutzername wird bereits verwendet!";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  
}
