package home.xflier.authn.entity;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * RoleAssignmentEntity
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Tag(name = "User Role Assignement Entity", description = "A simple implementation to assign roles to users")
@Entity
@Table(name = "user2role_tb")
public class RoleAssignmentEntity {
    
    private long id;
    private UserEntity user;
    private RoleEntity role;
    private String assignedBy;
    private LocalDateTime lastTimeStamp;

    @Column(name = "assigned_by", nullable = false, length = 255)
    public String getAssignedBy() {
        return assignedBy;
    }

    @Version
    @Column(name = "last_timestamp", nullable = true, insertable = false, updatable = true)   
    public LocalDateTime getLastTimeStamp() {
        return lastTimeStamp == null ? LocalDateTime.now() : lastTimeStamp;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    public UserEntity getUser() {
        return user;
    }

    @ManyToOne(cascade = {})
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    public RoleEntity getRole() {
        return role;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }
    
    public void setLastTimeStamp(LocalDateTime lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

}
