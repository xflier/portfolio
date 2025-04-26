package home.xflier.authn.dto;

import home.xflier.authn.dto.in.RoleAssignmentInDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoleAssignmentInValidator implements ConstraintValidator<RoleAssignmentInValidation, RoleAssignmentInDto> {

    @Override
    public boolean isValid(RoleAssignmentInDto value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        if (value.getRoleId() == 0 &&
                value.getRolename() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("roleId and rolename cannot be Both null or 0")
                    // .addPropertyNode("roleId")
                    .addConstraintViolation();
            return false;
        }
        if (value.getUserId() == 0 && value.getUsername() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("userId and username cannot be Both null or blank")
                    // .addPropertyNode("userId")
                    .addConstraintViolation();
            return false;
        }
        return true;
    }

}
