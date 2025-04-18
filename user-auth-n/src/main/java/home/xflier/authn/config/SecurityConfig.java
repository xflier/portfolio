package home.xflier.authn.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import home.xflier.authn.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtServletFilter jwtServletFilter;
    private final UserService userService;

    public SecurityConfig(JwtServletFilter jwtServletFilter, UserService userService) {
        this.jwtServletFilter = jwtServletFilter;
        this.userService = userService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        /* Disable CSRF Token with non Lambda way */
        /*
         * Customizer<CsrfConfigurer<HttpSecurity>> customizer = new Customizer<>() {
         * 
         * @Override
         * public void customize(CsrfConfigurer<HttpSecurity> t) {
         * t.disable();
         * }
         * };
         * 
         * http.csrf(customizer);
         */

        http.csrf(customizer -> customizer.disable())

        // allow login without JWT or username/passwd authentication
        .authorizeHttpRequests(auth -> auth.requestMatchers("/user/login").permitAll().anyRequest().authenticated())
        // .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        /* non lambda way to disable session, and make it stateless */
        // Customizer<SessionManagementConfigurer<HttpSecurity>> sessionManagementCustomizer = new Customizer<>() {

        //     @Override
        //     public void customize(SessionManagementConfigurer<HttpSecurity> t) {
        //         t.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //     }

        // };

        // http.sessionManagement(sessionManagementCustomizer);

        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        
        .httpBasic(Customizer.withDefaults())
        
        // .securityContext(context -> context .requireExplicitSave(false))
        // .requestCache(cache -> cache.disable())

        // .anonymous(anon -> anon.disable())
        

        // JWT Filter and username password authentication
        .addFilterBefore(jwtServletFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationProvider authProvider(@Qualifier("bcryptEncoder") BCryptPasswordEncoder bcryptEncoder) {

        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(this.userService);
        // provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); // no password encoder
        provider.setPasswordEncoder(bcryptEncoder);

        return provider;
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}