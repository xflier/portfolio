package home.xflier.authn.dto.out;

import lombok.Data;

/**
 * RoleOutDto is a Data Transfer Object (DTO) that represents the output data for a role.
 * It contains fields for the ID and role name.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Data
public class UserOutDto {
    private long id;
    private String username;
    private String email;
}
