package home.xflier.authn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion;

/**
 * Utility beans for the application.
 * <p>
 * This class provides a bean for BCryptPasswordEncoder with a specific version and strength.
 * </p>
 *
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */
@Configuration
public class UtilBeans {

    /**
     * Provides a BCryptPasswordEncoder bean with version $2A and strength 12.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public BCryptPasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder(BCryptVersion.$2A, 12);
    }
}
