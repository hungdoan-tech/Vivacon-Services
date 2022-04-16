package com.vivacon.common.validation;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = EmailExistsConstraintValidator.class)
public @interface EmailExistsConstraint {

    String message() default "Email is already existed";

    Class[] groups() default {};

    Class[] payload() default {};
}

