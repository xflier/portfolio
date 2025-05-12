package home.xflier.authn.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.in.RoleAssignmentInDto;
import home.xflier.authn.dto.out.RoleAssignmentOutDto;
import home.xflier.authn.entity.RoleAssignmentEntity;
import home.xflier.authn.mapper.UserMapper;
import home.xflier.authn.repo.RoleAssignmentRepo;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * RoleAssignmentService
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Tag(name = "Role Assignment Service", description = "Service for managing role assignments")
@Service
@Slf4j
public class RoleAssignmentService {

    @Autowired
    private RoleAssignmentRepo roleAssignmentRepo;

    @Autowired
    private UserMapper userMapper;

    @CacheEvict(value = "roleAssignment", allEntries = true)
    public RoleAssignmentOutDto save(RoleAssignmentInDto roleAssignment) {
        RoleAssignmentEntity entity = userMapper.toRoleAssignmentEntity(roleAssignment);
        return userMapper.toRoleAssignmentDto(roleAssignmentRepo.save(entity));
    }

    @Cacheable(value = "roleAssignment", key = "#id")
    public List<RoleAssignmentOutDto> findByUserId(long id) {
        List<RoleAssignmentEntity> roles = roleAssignmentRepo.findByUserId(id);
        return userMapper.toRoleAssignmentDto(roles);
    }

    public void deleteByUserRoleId(long userId, int roleId) {
        roleAssignmentRepo.deleteByUserIdAndRoleId(userId, roleId);
    }

}
