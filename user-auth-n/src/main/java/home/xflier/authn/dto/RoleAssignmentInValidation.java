package home.xflier.authn.dto;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

/**
 * Custom validation annotation for role assignment input.
 * Validates that the role name is one of the allowed values.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Constraint(validatedBy = {RoleAssignmentInValidator.class})
public @interface RoleAssignmentInValidation {
    String message() default "Invalid role assignment input";
    Class<?>[]  groups() default {};
    Class<? extends Payload>[] payload() default {};
    String[] allowedRolenames = {"ADMIN", "USER"};
}
