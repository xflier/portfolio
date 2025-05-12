package home.xflier.authn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * A simple user authentication application :
 * 
 * 	1. user table setup in a database
 * 	2. allow user register(add new user) and login
 * 	3. login with JWT after login
 *  4. login with user/passwd authentication
 * 	5. REST APIs in xml or json formats (STATELESS)
 *  6. Disabled CSRF token
 *  7. user database access is cached with EhCache
 * 
 * 
 * @author xflier
 * @version 1.0
 * @since 2023-10-01
 */

@SpringBootApplication
@EnableCaching
public class UserAuthNApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserAuthNApplication.class, args);
	}

}
