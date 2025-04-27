package home.xflier.authn.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import home.xflier.authn.dto.in.RoleAssignmentInDto;
import home.xflier.authn.dto.out.RoleAssignmentOutDto;
import home.xflier.authn.service.RoleAssignmentService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * RoleAssignmentController
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@RestController
@RequestMapping("/role-assignments")
@Validated
@Slf4j
@Tag(name = "Role Assignment APIs", description = "Operations related to role assignments, such as add, query, etc.")
@Transactional
public class RoleAssignmentController {

    @Autowired
    private RoleAssignmentService roleAssignmentService;

    /**
     * Add a role assignment
     *
     * @param roleAssignment
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoleAssignmentOutDto> add(HttpServletRequest req,
            @Valid @RequestBody RoleAssignmentInDto roleAssignment) {

        log.info("Processing request - /role-assignments/add - POST");

        RoleAssignmentOutDto roleAssignmentDto = roleAssignmentService.save(roleAssignment);

        return new ResponseEntity<>(roleAssignmentDto, HttpStatus.CREATED);

    }

    /**
     * Find role assignments by user id
     *
     * @param id
     * @return
     */
    @GetMapping("user/id/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<RoleAssignmentOutDto>> findByUserId(@PathVariable(name = "userId", required = true) long id) {

        log.info("Processing request - /role-assignments/user/{userId} - GET");

        List<RoleAssignmentOutDto> roleAssignments = roleAssignmentService.findByUserId(id);

        return new ResponseEntity<>(roleAssignments, HttpStatus.OK);
    }

    /**
     * Delete role assignments by user id and role id
     *
     * @param userId
     * @param roleId
     * @return
     */
    @DeleteMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteByUserId(@RequestParam("userId") long userId,
            @RequestParam ("roleId") int roleId ) {

        log.info("Processing request - /role-assignments - DELETE");
        log.info("userId: " + userId);
        log.info("roleId: " + roleId);

        roleAssignmentService.deleteByUserRoleId(userId, roleId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
