package home.xflier.authn.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * RoleInDto is a Data Transfer Object (DTO) that represents the input data for a role.
 * It contains a single field, rolename, which is validated to ensure it matches one of the predefined role names.
 * The class is annotated with Lombok's @Data to automatically generate getters, setters, and other utility methods.
 *  
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Data
public class RoleInDto {

    @Schema(description = "Role name", example = "ROLE_ADMIN, ROLE_GUEST or ROLE_USER")
    // @NotBlank(message = "Role name cannot be blank")
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_GUEST|ROLE_USER)$", message = "Role name must be one of these values ROLE_ADMIN, ROLE_GUEST, or ROLE_USER")
    private String rolename;
}
