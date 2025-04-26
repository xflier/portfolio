package home.xflier.authn.dto.in;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class RoleInDto {

    @Schema(description = "Role name", example = "ROLE_ADMIN, ROLE_GUEST or ROLE_USER")
    // @NotBlank(message = "Role name cannot be blank")
    @Pattern(regexp = "^(ROLE_ADMIN|ROLE_GUEST|ROLE_USER)$", message = "Role name must be one of these values ROLE_ADMIN, ROLE_GUEST, or ROLE_USER")
    private String rolename;
}
