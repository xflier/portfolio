package home.xflier.authn.mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import home.xflier.authn.dto.in.RoleAssignmentInDto;
import home.xflier.authn.dto.in.RoleInDto;
import home.xflier.authn.dto.in.UserInDto;
import home.xflier.authn.dto.out.RoleAssignmentOutDto;
import home.xflier.authn.dto.out.RoleOutDto;
import home.xflier.authn.dto.out.UserOutDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.entity.RoleAssignmentEntity;
import home.xflier.authn.entity.RoleEntity;
import home.xflier.authn.entity.UserEntity;

/**
 * UserMapper
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Mapper(componentModel = "spring", uses = RoleAssignmentMapper.class)
public interface UserMapper{

    @Mapping(source = "username", target = "username")
    @Mapping(target = "rolesAssigned", ignore = true)
    public UserEntity toUserEntity(UserInDto user);

    // @Mapping(target = "username",source = "username", qualifiedByName = "hideDefaultUsername")
    // @Mapping(target = "email",source = "email", qualifiedByName = "hideDefaultEmail")
    public UserOutDto toUserDto(UserEntity user); 

    @Mapping(target = "authorities", expression = "java(loadAuthorities(user))")
    public UserPrincipal toUserPrincipal(UserEntity user); 

    public List<UserOutDto> toUserDtoList(List<UserEntity> users);

    @Mapping(target = "id", ignore = true)
    public RoleEntity toRoleEntity(RoleInDto role);

    public RoleOutDto toRoleDto(RoleEntity role);

    public List<RoleOutDto> toRoleDtoList(List<RoleEntity> roles);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "user", source = "roleAssignment", qualifiedByName = "toUserEntity")
    @Mapping(target = "role", source = "roleAssignment", qualifiedByName = "toRoleEntity")
    @Mapping(target = "assignedBy", source = "roleAssignment", qualifiedByName = "toAssignedBy")
    @Mapping(target = "lastTimeStamp", ignore = true)
    public RoleAssignmentEntity toRoleAssignmentEntity(RoleAssignmentInDto roleAssignment);

    public RoleAssignmentOutDto toRoleAssignmentDto(RoleAssignmentEntity roleAssignment);

    public List<RoleAssignmentOutDto> toRoleAssignmentDto(List<RoleAssignmentEntity> roleAssignment);

    default Collection<? extends GrantedAuthority>  loadAuthorities(UserEntity user) {
        List<GrantedAuthority> authorities = user.getRolesAssigned().stream()
            .map(role -> new SimpleGrantedAuthority(role.getRole().getRolename()))
            .collect(Collectors.toList());
                return authorities;
    }

    // @Named("hideDefaultUsername")
    // default String hideDefaultUsername (String username) {
    //     if (username == "None" ) {
    //         return null;
    //     }
    //     return username;   
    // }

    // @Named("hideDefaultEmail")
    // default String hideDefaultEmail (String email) {
    //     if (email == "none@none.com" ) {
    //         return null;
    //     }
    //     return email;   
    // }
}
