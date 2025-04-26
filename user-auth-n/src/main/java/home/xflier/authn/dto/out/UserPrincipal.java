package home.xflier.authn.dto.out;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.NoArgsConstructor;

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
