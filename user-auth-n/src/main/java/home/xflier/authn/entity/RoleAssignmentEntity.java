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

@Tag(name = "User Role Assignement Entity", description = "A simple implementation to assign roles to users")
@Entity
@Table(name = "user2role_tb")
public class RoleAssignmentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private UserEntity user;

    @ManyToOne(cascade = {})
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private RoleEntity role;

    @Column(name = "assigned_by", nullable = false, length = 255)
    private String assignedBy;

    public String getAssignedBy() {
        return assignedBy;
    }

    public void setAssignedBy(String assignedBy) {
        this.assignedBy = assignedBy;
    }

    public LocalDateTime getLastTimeStamp() {
        return lastTimeStamp;
    }

    public void setLastTimeStamp(LocalDateTime lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    @Column(name = "last_timestamp", nullable = false, insertable = false, updatable = false)   
    private LocalDateTime lastTimeStamp;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    
}
