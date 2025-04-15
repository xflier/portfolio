package home.xflier.authn.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import home.xflier.authn.dto.in.UserInDto;
import home.xflier.authn.dto.out.UserOutDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(source = "username", target = "username")
    public UserEntity toEntity(UserInDto user);

    // @Mapping(target = "passwd", ignore = true)
    public UserOutDto toDto(UserEntity user); 

    @Mapping(target = "authorities", ignore = true)
    public UserPrincipal toPrincipal(UserEntity user); 

    public List<UserOutDto> toDtoList(List<UserEntity> users);
}
