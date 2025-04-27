package home.xflier.authn.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.in.RoleInDto;
import home.xflier.authn.dto.out.RoleOutDto;
import home.xflier.authn.entity.RoleEntity;
import home.xflier.authn.mapper.UserMapper;
import home.xflier.authn.repo.RoleRepo;

/**
 * RoleService
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired 
    private UserMapper userMapper;

    public RoleOutDto addRole(RoleInDto roleIn) {
        RoleEntity role = roleRepo.save(userMapper.toRoleEntity(roleIn));
        return userMapper.toRoleDto(role);
    }

    public List<RoleOutDto> findAll() {
        List<RoleEntity> roles = roleRepo.findAll();
        return userMapper.toRoleDtoList(roles);
    }

    public void deleteById(int id) {
        roleRepo.deleteById(id);
    }

    @Cacheable(value = "roleCacheById", key = "#id") 
    public RoleOutDto findById(int id) {
        RoleEntity role = roleRepo.findById(id).orElseThrow();
        return userMapper.toRoleDto(role);
    }

}
