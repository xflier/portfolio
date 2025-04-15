package home.xflier.authn.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import home.xflier.authn.dto.in.UserInDto;
import home.xflier.authn.dto.out.UserOutDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.entity.UserEntity;
import home.xflier.authn.mapper.UserMapper;
import home.xflier.authn.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    private final UserMapper mapper;
    private final UserRepo repo;
    private final BCryptPasswordEncoder bcryptEncoder;

    public UserService(UserMapper mapper, UserRepo repo, BCryptPasswordEncoder bcryptEncoder) {
        this.mapper = mapper;
        this.repo = repo;
        this.bcryptEncoder = bcryptEncoder;
    }

    public UserOutDto add(UserInDto user) {
        LOGGER.info("adding a user ... " + user.getUsername());
        UserEntity newUser = mapper.toEntity(user);
        newUser.setPasswd(bcryptEncoder.encode(user.getPasswd()));
        UserEntity userAdded = repo.save(newUser);
        return mapper.toDto(userAdded);
    }

    public UserOutDto findById(long id) {
        LOGGER.info("finding a user by an id ... " + id);
        UserEntity user = repo.findById(id).orElseThrow();
        return mapper.toDto(user);
    }

    public UserPrincipal getUserPrincipal(String name) {
        LOGGER.info("retrieving a user principal by a name ... " + name);
        UserEntity user = repo.findByUsername(name).orElseThrow();
        return mapper.toPrincipal(user);
    }

    public Page<UserOutDto> partialSearch(String partialValue, int offset, int limit) {
        Pageable pageable = PageRequest.of((int) (offset / limit), limit);
        Page<UserEntity> users = repo.partialSearch(partialValue, pageable);
        return users.map(mapper::toDto);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return getUserPrincipal(username);
    }

    public boolean authenticate(UserInDto user, AuthenticationManager authManager) {
        Authentication authentication = authManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPasswd()));
        return authentication.isAuthenticated();
    }
}
