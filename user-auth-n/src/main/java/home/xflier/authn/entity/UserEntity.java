package home.xflier.authn.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Version;

/**
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Tag(name = "User Entity", description = "A simple User Entity with minimal information, corresponding to databale table structure")
@Entity
@Table(name = "user_tb")
@NamedEntityGraph(name = "UserEntity.rolesAssigned", attributeNodes = @NamedAttributeNode("rolesAssigned") )
public class UserEntity implements Serializable{

    private long id;
    private String username;
    private String passwd;
    private String email;
    private LocalDateTime lastTimeStamp;

    private Set<RoleAssignmentEntity> rolesAssigned = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(long id, String username, String passwd, String email) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }

    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    @Column(name = "username", unique = true, nullable = false, length = 255)
    public String getUsername() {
        return username;
    }

    @Column(name = "passwd", nullable = false, length = 255)
    public String getPasswd() {
        return passwd;
    }

    @Column(name = "email", nullable = false, length = 255)
    public String getEmail() {
        return email;
    }

    @Version
    @Column(name = "last_timestamp", nullable = false, insertable = false, updatable = true)   
    public LocalDateTime getLastTimeStamp() {
        return this.lastTimeStamp == null ? LocalDateTime.now() : this.lastTimeStamp;
    }

    @OneToMany(mappedBy = "user", fetch = jakarta.persistence.FetchType.LAZY, cascade = {})
    public Set<RoleAssignmentEntity> getRolesAssigned() {
        return rolesAssigned;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setLastTimeStamp(LocalDateTime lastTimeStamp) {
        this.lastTimeStamp = lastTimeStamp;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRolesAssigned(Set<RoleAssignmentEntity> rolesAssigned) {
        this.rolesAssigned = rolesAssigned;
    }

}
