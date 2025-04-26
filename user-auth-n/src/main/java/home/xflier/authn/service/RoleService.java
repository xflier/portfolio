package home.xflier.authn.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.out.RoleOutDto;
import home.xflier.authn.entity.RoleEntity;
import home.xflier.authn.mapper.UserMapper;
import home.xflier.authn.repo.RoleRepo;

@Service
public class RoleService {

    @Autowired
    private RoleRepo roleRepo;
    
    @Autowired 
    private UserMapper userMapper;

    public RoleOutDto addRole(RoleEntity roleIn) {
        RoleEntity role = roleRepo.save(roleIn);
        return userMapper.toRoleDto(role);
    }

    public List<RoleEntity> findAll() {
        List<RoleEntity> roles = roleRepo.findAll();
        return roles;
    }

    public void deleteById(int id) {
        roleRepo.deleteById(id);
    }

    public RoleEntity findById(int id) {
        return roleRepo.findById(id).orElseThrow();
    }

}
