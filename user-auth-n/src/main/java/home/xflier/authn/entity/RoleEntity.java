package home.xflier.authn.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * RoleEntity.java
 * 
 * This class represents a role entity in the system.
 * It is used to differentiate between regular users with view rights and admin roles with management rights.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Tag(name = "User Role Entity", description = "A simple role implementation to differentiate regular user with view right, and admin role with management")
@Entity
@Table(name = "role_tb")
public class RoleEntity {
    
    private int id;
    private String rolename;
    private LocalDateTime lastTimeStamp;

    @Version
    @Column(name = "last_timestamp", nullable = false, insertable = false, updatable = true)   
    public LocalDateTime getLastTimeStamp() {
        return lastTimeStamp == null ? LocalDateTime.now() : lastTimeStamp;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int getId() {
        return id;
    }

    @Column(name = "rolename", nullable = false, length = 255)
    public String getRolename() {
        return rolename;
    }

    public void setLastTimeStamp(LocalDateTime lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }
}
