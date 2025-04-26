package home.xflier.authn.dto.out;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class RoleAssignmentOutDto {
    private long id;
    private UserOutDto user;
    private RoleOutDto role;
    private String assignedBy;
    private LocalDateTime lastTimeStamp;
}
