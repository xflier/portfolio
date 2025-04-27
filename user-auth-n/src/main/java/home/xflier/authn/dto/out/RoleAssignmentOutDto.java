package home.xflier.authn.dto.out;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * RoleAssignmentOutDto is a Data Transfer Object (DTO) that represents the output data for a role assignment.
 * It contains fields for the ID, user, role, assignedBy, and lastTimeStamp.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Data
public class RoleAssignmentOutDto {
    private long id;
    private UserOutDto user;
    private RoleOutDto role;
    private String assignedBy;
    private LocalDateTime lastTimeStamp;
}
