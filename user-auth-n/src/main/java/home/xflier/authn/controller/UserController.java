package home.xflier.authn.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import home.xflier.authn.dto.in.UserInDto;
import home.xflier.authn.dto.out.UserOutDto;
import home.xflier.authn.service.JwtService;
import home.xflier.authn.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@Validated
@RequestMapping(value = "/user/", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
@Tag(name = "User APIs", description = "Operations related to users")
public class UserController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final AuthenticationManager authManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authManager, JwtService jwtService) {
        this.userService = userService;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    @Operation(summary = "create a new user", description = "add a new user into database")
    @PostMapping(value = "add", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE })
    public ResponseEntity<UserOutDto> add(HttpServletRequest req, @RequestBody @Valid UserInDto user) {
        LOGGER.info("processing request - /user/add - POST");
        UserOutDto userDto = userService.add(user);
        LOGGER.info("user is addedd  -  " + userDto.getUsername());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @Operation(summary = "query a user by an id", description = "return the user details except password")
    @GetMapping("id/{userId}")
    public ResponseEntity<UserOutDto> getUserById(HttpServletRequest req, @PathVariable("userId") long id) {
        UserOutDto user = userService.findById(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Operation(summary = "search a user with a partial value on username", description = "Paginated Response")
    @GetMapping("search/{partialValue}/{offset}/{limit}")
    public ResponseEntity<Page<UserOutDto>> partialSearch(HttpServletRequest req,
            @PathVariable("partialValue") String p,
            @PathVariable("offset") int offset, @PathVariable("limit") int limit) {
        return new ResponseEntity<>(userService.partialSearch(p, offset, limit), HttpStatus.OK);
    }

    @Operation(summary = "login with username/password, and get a JWT token in the header", description = "User JWT Token for future requests")
    @PostMapping("login")
    public ResponseEntity<String> login(HttpServletRequest req, HttpServletResponse res, @RequestBody UserInDto user) {

        boolean isAuthenticated = userService.authenticate(user, this.authManager);

        if (isAuthenticated) {
            String token = jwtService.generateToken(user.getUsername());
            res.addHeader("Authorization", "Bearer " + token);

            LOGGER.info("JWT Token = " + token);
            return new ResponseEntity<>("login succeeded!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("login failed!", HttpStatus.BAD_REQUEST);
        }
    }
}