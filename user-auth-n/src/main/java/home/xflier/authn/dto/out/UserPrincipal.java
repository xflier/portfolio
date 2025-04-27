package home.xflier.authn.dto.out;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * UserPrincipal is a Data Transfer Object (DTO) that represents the principal user in the authentication system.
 * It implements UserDetails interface from Spring Security.
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Data
@NoArgsConstructor
public class UserPrincipal implements UserDetails{
    private long id;
    private String username;
    private String passwd;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // return Collections.singleton(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
    
    @Override
    public String getPassword() {
        return passwd;
    }

}
