package andrehsvictor.retenx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
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

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.authorizeHttpRequests(requests -> requests
                .requestMatchers(HttpMethod.GET, ALLOWED_PATHS_WITH_GET_METHOD).permitAll()
                .requestMatchers(HttpMethod.POST, ALLOWED_PATHS_WITH_POST_METHOD).permitAll()
                .requestMatchers(HttpMethod.PUT, ALLOWED_PATHS_WITH_PUT_METHOD).permitAll()
                .anyRequest().authenticated());
        http.oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return http.build();
    }

}
