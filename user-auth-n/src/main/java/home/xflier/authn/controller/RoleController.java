package home.xflier.authn.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import home.xflier.authn.dto.in.RoleInDto;
import home.xflier.authn.dto.out.RoleOutDto;
import home.xflier.authn.dto.out.UserPrincipal;
import home.xflier.authn.service.RoleService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RoleController
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@RestController
@Validated
@RequestMapping("/role")
@Slf4j
@Tag(name = "Role APIs", description = "Operations related to roles, such as add, query, etc.")
public class RoleController {

    @Autowired
    private RoleService roleService;

    /**
     * Add a new role
     *
     * @param req
     * @param role
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleOutDto> add(HttpServletRequest req, @Valid @RequestBody RoleInDto role) {

        RoleOutDto roleOut = roleService.addRole(role);

        return new ResponseEntity<>(roleOut, HttpStatus.CREATED);
    }

    /**
     * Retrieve all roles
     *
     * @param req
     * @param role
     * @return
     */
    @GetMapping("/all")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RoleOutDto>> findAll(@AuthenticationPrincipal UserPrincipal currentUser) {

        log.info("current user: " + currentUser.getUsername());
        log.info("current user role: " + currentUser.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .toList());
        
        List<RoleOutDto> roles = roleService.findAll();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    /**
     * Delete a role by ID
     *
     * @param id
     * @return
     */
    @DeleteMapping("/id/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable(name = "id", required = true) int id) {

        roleService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieve a role by ID
     *
     * @param id
     * @return
     */
    @GetMapping("/id/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RoleOutDto> findById(@PathVariable(name = "id", required = true) int id) {

        RoleOutDto role = roleService.findById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }
    
}
