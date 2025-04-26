package home.xflier.authn.dto.in;

import home.xflier.authn.dto.RoleAssignmentInValidation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@RoleAssignmentInValidation
public class RoleAssignmentInDto {

    @Schema(description = "User ID", example = "1")
    private long userId;

    @Schema(description = "Role ID", example = "1")
    private int roleId;

    @Schema(description = "Role name", example = "ROLE_ADMIN, ROLE_GUEST or ROLE_USER")
    // @Pattern(regexp = "^(ROLE_ADMIN|ROLE_GUEST|ROLE_USER)$", message = "Role name must be one of these values ROLE_ADMIN, ROLE_GUEST, or ROLE_USER")
    private String rolename;

    @Schema(description = "Username", example = "bobr")
    private String username;
}
