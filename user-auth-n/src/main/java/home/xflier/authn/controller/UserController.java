package home.xflier.authn.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.xflier.authn.dto.in.LoginInDto;
import home.xflier.authn.dto.in.UserInDto;
import home.xflier.authn.dto.out.LoginOutDto;
import home.xflier.authn.dto.out.UserOutDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.service.JwtService;
import home.xflier.authn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * This controller handles user-related operations such as adding a new user,
 * retrieving user details, searching for users, and user login.
 * <p>
 * It provides endpoints for creating a new user, retrieving user information
 * by ID, searching for users with a partial username, and logging in with
 * username and password to obtain a JWT token.
 * </p>
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@RestController
@Validated
@RequestMapping(value = "/user", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Tag(name = "User APIs", description = "Operations related to users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private JwtService jwtService;

    /**
     * add a new user
     * 
     * @param req
     * @param user
     * @return
     */
    @Operation(summary = "create a new user", description = "add a new user into database")
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserOutDto> add(HttpServletRequest req, @RequestBody @Valid UserInDto user) {
        LOGGER.info("processing request - /user - POST");
        UserOutDto userDto = userService.add(user);
        LOGGER.info("user is addedd  -  " + userDto.getUsername());
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }

    /**
     * Retrieve a user
     * 
     * @param req
     * @param user
     * @return
     */
    @Operation(summary = "query a user by an id", description = "return the user details except password")
    @GetMapping("/id/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserOutDto> getUserById(HttpServletRequest req, @PathVariable("userId") long id) {
        UserOutDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    /**
     * Search users with a partial value on username
     * 
     * @param partialValue
     * @param req
     * @param offset
     * @param limit
     * @return
     */
    @Operation(summary = "search a user with a partial value on username", description = "Paginated Response")
    @GetMapping("/partial-username/{partialValue}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<Page<UserOutDto>> partialSearch(HttpServletRequest req,
            @PathVariable(value = "partialValue", required = true) String p,
            @RequestParam(defaultValue = "0", required = false) @Qualifier("offset") int offset,
            @RequestParam(defaultValue = "2", required = false) @Qualifier("limit") int limit) {
        return new ResponseEntity<>(userService.partialSearch(p, offset, limit), HttpStatus.OK);
    }

    /**
     * Login with username and password and get a JWT token in the header
     * 
     * @param req
     * @param user
     * @return
     */
    @Operation(summary = "login with username/password, and get a JWT token in the header", description = "User JWT Token for future requests")
    @PostMapping("/token")
    public ResponseEntity<LoginOutDto> login(HttpServletRequest req, HttpServletResponse res,
            @RequestBody LoginInDto user) {

        boolean isAuthenticated = userService.authenticate(user, this.authManager);

        if (isAuthenticated) {
            LoginOutDto dto = jwtService.generateToken(user.getUsername());
            res.addHeader("Authorization", "Bearer " + dto.getAccessToken());

            dto.setRefreshToken(jwtService.generateToken(null, true));
            dto.setAccessExpiresIn(jwtService.getAccessTokenExpiration());
            dto.setRefreshExpiresIn(jwtService.getRefreshTokenExpiration());

            LOGGER.info("Access JWT Token = " + dto.getAccessToken());
            LOGGER.info("Refresh JWT Token = " + dto.getAccessToken());

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @Operation(summary = "refresh a JWT token", description = "User JWT Token for future requests")
    @PostMapping("/refresh-token")
    public ResponseEntity<LoginOutDto> refreshToken(HttpServletRequest req, HttpServletResponse res,
            @RequestBody String refreshToken) {

        UserPrincipal user = jwtService.validateToken(refreshToken, true);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user,
                null, user.getAuthorities());
        // authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
        SecurityContextHolder.getContext().setAuthentication(authToken);

        LoginOutDto dto = jwtService.generateToken(user.getUsername());
        dto.setRefreshToken(refreshToken);
        dto.setRefreshExpiresIn(user.getRefreshTokenExpiration());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}