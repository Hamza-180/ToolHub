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
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/login", "/register")
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register").permitAll()
                .requestMatchers("/api/reservations/**").authenticated()  // Alleen voor geauthenticeerde gebruikers
                .anyRequest().permitAll()  // Alle andere verzoeken zonder authenticatie
            )
            .formLogin(form -> form
                .loginPage("/login")  // Aangepaste loginpagina
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect naar dashboard na succesvolle login
            )
            .logout(logout -> logout
                .logoutSuccessUrl("/login?logout")  // Redirect naar loginpagina na logout
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Alleen sessies maken wanneer nodig
                .invalidSessionUrl("/login")
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
