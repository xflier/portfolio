package home.xflier.authn.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import home.xflier.authn.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * This filter is used to validate the JWT token in the request header.
 * If the token is valid, it sets the authentication in the security context.
 * It is executed once per request.
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@Component
@Slf4j
public class JwtServletFilter extends OncePerRequestFilter {

    private JwtService jwtService;

    /**
     * This method is called once per request.
     * It reads the JWT token from the request header, validates it,
     * and sets the authentication in the security context.
     * @param request the HttpServletRequest
     * @param response the HttpServletResponse
     * @param filterChain the FilterChain
     * @throws ServletException if an error occurs during the filter chain
     * @throws IOException if an error occurs during the filter chain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // try to read the JWT from the request header
        String authHeader = request.getHeader("Authorization");
        String token = null;

        if (authHeader != null && authHeader.startsWith(("Bearer "))) {
            token = authHeader.substring(7);
            logger.info("JWT token = " + token);
        }

        if (token != null &&
        // make sure it is not authorized yet
                SecurityContextHolder.getContext().getAuthentication() == null) {

            // JWT token was found in the header, and it is not authorized yet
            // validate the JWT token, and get the user details

            // once token is validated, create UsernamePasswordAuthenticationToken
            UserDetails userDetails = jwtService.validateToken(token);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                    null, userDetails.getAuthorities());
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
        filterChain.doFilter(request, response);
    }

    /**
     * This method is used to set the JwtService.  It is called by Spring
     * @param jwtService
     */
    @Autowired
    public void setJwtService(JwtService jwtService) {
        this.jwtService = jwtService;
    }
}
