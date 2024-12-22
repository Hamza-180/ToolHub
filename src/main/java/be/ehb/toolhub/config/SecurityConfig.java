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
                .ignoringRequestMatchers("/login", "/register", "/logout")
            )
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register").permitAll()  // Zorg ervoor dat login en register zonder authenticatie toegankelijk zijn
                .requestMatchers("/api/reservations/**").authenticated()  // Alleen voor geauthenticeerde gebruikers
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Aangepaste loginpagina
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect naar dashboard na succesvolle login
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Zorg ervoor dat de logout URL correct is ingesteld
                .logoutSuccessUrl("/login?logout")  // Redirect naar loginpagina na logout.invalidateHttpSession(true)  // Vernietig de sessie bij uitloggen
                .clearAuthentication(true)
                .permitAll()  // Maak logout openbaar toegankelijk
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Alleen sessies maken wanneer nodig
                .invalidSessionUrl("/login")  // Redirect naar login als de sessie ongeldig is
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
