package home.xflier.authn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.out.RoleAssignmentOutDto;
import home.xflier.authn.entity.RoleAssignmentEntity;
import home.xflier.authn.mapper.UserMapper;
import home.xflier.authn.repo.RoleAssignmentRepo;

@Service
public class RoleAssignmentService {

    @Autowired
    private RoleAssignmentRepo roleAssignmentRepo;

    @Autowired
    private UserMapper userMapper;

    public RoleAssignmentEntity save(RoleAssignmentEntity entity) {
        return roleAssignmentRepo.save(entity);
    }

    public List<RoleAssignmentOutDto> findByUserId(long id) {
        List<RoleAssignmentEntity> roles = roleAssignmentRepo.findByUserId(id);
        return userMapper.toRoleAssignmentDto(roles);
    }

    public void deleteByUserRoleId(long userId, int roleId) {
        roleAssignmentRepo.deleteByUserIdAndRoleId(userId, roleId);
    }

}
