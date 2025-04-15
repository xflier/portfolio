package home.xflier.authn.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import home.xflier.authn.entity.UserEntity;


public interface UserRepo extends JpaRepository<UserEntity, Long>{

    public Optional<UserEntity> findByUsername(String username);

    @Query("select user from UserEntity user where user.username like :v%")
    public Page<UserEntity> partialSearch(@Param("v") String s, Pageable pageable);
}
