package home.xflier.authn.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import home.xflier.authn.entity.RoleEntity;

/**
 * Role repository interface for managing role entities.
 * Extends JpaRepository to provide CRUD operations.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Repository
public interface RoleRepo extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByRolename(String roleName);


}
