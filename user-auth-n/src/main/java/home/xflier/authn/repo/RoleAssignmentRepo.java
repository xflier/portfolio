package home.xflier.authn.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import home.xflier.authn.entity.RoleAssignmentEntity;
import java.util.List;

@Repository
public interface RoleAssignmentRepo extends CrudRepository<RoleAssignmentEntity, Long> {
    // Additional query methods can be defined here if needed
    // For example, to find all role assignments for a specific user:
    // List<UserRoleAssignmentEntity> findByUserId(Long userId);
    // Or to find all role assignments for a specific role:
    // List<UserRoleAssignmentEntity> findByRoleId(Long roleId);
    // Or to find all role assignments for a specific user and role:
    // List<UserRoleAssignmentEntity> findByUserIdAndRoleId(Long userId, Long
    // roleId);

    List<RoleAssignmentEntity> findByUserId(long id);

    void deleteByUserIdAndRoleId(long userId, int roleId);
}
