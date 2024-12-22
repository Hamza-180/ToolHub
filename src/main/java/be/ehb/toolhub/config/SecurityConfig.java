package be.ehb.toolhub.config;

import be.ehb.toolhub.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())  // Disable CSRF protection
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register").permitAll()  // Ensure login and register are accessible without authentication
                .requestMatchers("/api/reservations/**").authenticated()  // Only authenticated users can access reservations
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Custom login page
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect to dashboard after successful login
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Ensure logout URL is correctly set
                .logoutSuccessUrl("/login?logout")  // Redirect to login page after logout
                .invalidateHttpSession(true)  // Invalidate the session on logout
                .clearAuthentication(true)
                .permitAll()  // Make logout publicly accessible
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Only create sessions when needed
                .invalidSessionUrl("/login")  // Redirect to login if the session is invalid
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
