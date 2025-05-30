package home.xflier.authn.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.xflier.authn.entity.UserEntity;

/**
 * User repository interface for managing user entities.
 * Provides methods to find users by username and perform partial searches.
 * Also includes a method to fetch user entities with their associated roles.
 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
public interface UserRepo extends JpaRepository<UserEntity, Long>{

    public Optional<UserEntity> findByUsername(String username);

    @Query("select user from UserEntity user where user.username like :v%")
    public Page<UserEntity> partialSearch(@Param("v") String s, Pageable pageable);

    @EntityGraph(value = "rolesAssigned", type = EntityGraphType.FETCH)
    public Optional<UserEntity> findWithRoleByUsername(String name);
}
