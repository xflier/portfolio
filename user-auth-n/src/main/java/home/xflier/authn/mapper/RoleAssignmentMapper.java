package home.xflier.authn.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import home.xflier.authn.dto.in.RoleAssignmentInDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.entity.RoleEntity;
import home.xflier.authn.entity.UserEntity;
import home.xflier.authn.repo.RoleRepo;
import home.xflier.authn.repo.UserRepo;

/**
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Mapper(componentModel = "spring")
public class RoleAssignmentMapper {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RoleRepo roleRepo;

    @Named("toUserEntity")
    public UserEntity toUserEntity(RoleAssignmentInDto roleAssignmentInDto) {
        if (roleAssignmentInDto == null) {
            throw new IllegalArgumentException("RoleAssignmentInDto cannot be null");
        }

        UserEntity user = null;

        if (roleAssignmentInDto.getUserId() > 0) {
            user = new UserEntity();
            user.setId(roleAssignmentInDto.getUserId());
        } else {
            user = userRepo.findByUsername(roleAssignmentInDto.getUsername())
                    .orElseThrow(
                            () -> new IllegalArgumentException("User not found: " + roleAssignmentInDto.getUsername()));
        }

        return user;
    }

    @Named("toRoleEntity")
    public RoleEntity toRoleEntity(RoleAssignmentInDto roleAssignmentInDto) {
        if (roleAssignmentInDto == null) {
            throw new IllegalArgumentException("RoleAssignmentInDto cannot be null");
        }

        RoleEntity role = null;

        if (roleAssignmentInDto.getRoleId() > 0) {
            role = new RoleEntity();
            role.setId(roleAssignmentInDto.getRoleId());
        } else {
            role = roleRepo.findByRolename(roleAssignmentInDto.getRolename())
                    .orElseThrow(
                            () -> new IllegalArgumentException("Role not found: " + roleAssignmentInDto.getRolename()));
        }

        return role;
    }

    @Named("toAssignedBy")
    public String toAssignedBy(RoleAssignmentInDto roleAssignmentInDto) {
        if (roleAssignmentInDto == null) {
            throw new IllegalArgumentException("RoleAssignmentInDto cannot be null");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        String loginUser = userPrincipal.getUsername();

        return loginUser;
    }
}