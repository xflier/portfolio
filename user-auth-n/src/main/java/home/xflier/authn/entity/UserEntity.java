package home.xflier.authn.entity;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * A simple User Entity with minimal information, corresponding to databale table structure
 */

@Tag(name = "User Entity", description = "A simple User Entity with minimal information, corresponding to databale table structure")
@Entity
@Table(name = "user_tb", schema = "data01")
public class UserEntity implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;


    @Column(name = "passwd", nullable = false, length = 255)
    private String passwd;

    @Column(name = "email", nullable = false, length = 255)
    private String email;

    public UserEntity() {
        this.username = "None";
        this.passwd = "None";
        this.email = "none@none.com";
    }

    public long getId() {
        return id;
    }

    public UserEntity(long id, String username, String passwd, String email) {
        this.id = id;
        this.username = username;
        this.passwd = passwd;
        this.email = email;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
