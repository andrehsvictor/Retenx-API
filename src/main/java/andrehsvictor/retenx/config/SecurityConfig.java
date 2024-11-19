package andrehsvictor.retenx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    public static final String[] ALLOWED_PATHS_WITH_PUT_METHOD = {
            "/api/v1/users/password",
    };

    public static final String[] ALLOWED_PATHS_WITH_POST_METHOD = {
            "/api/v1/users"
    };

    public static final String[] ALLOWED_PATHS_WITH_GET_METHOD = {
            "/api/v1/users",
            "/api/v1/users/{id}",
            "/api/v1/decks",
            "/api/v1/decks/{id}",
    };
    
}
