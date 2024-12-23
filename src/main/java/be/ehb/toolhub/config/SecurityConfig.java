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

            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/login", "/register").permitAll()  // Zorg ervoor dat login en register toegankelijk zijn zonder authenticatie
                .requestMatchers("/api/reservations/**").authenticated()  // Alleen geauthenticeerde gebruikers kunnen reserveringen openen
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Aangepaste inlogpagina
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect naar dashboard na succesvolle login
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Zorg ervoor dat de logout-URL correct is ingesteld
                .logoutSuccessUrl("/login?logout")  // Redirect naar login-pagina na logout
                .invalidateHttpSession(true)  // Vernietig de sessie bij logout
                .clearAuthentication(true)
                .permitAll()  // Zorg ervoor dat logout openbaar toegankelijk is
            )
                .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/auth/login", "/api/auth/register","/api/**")
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Maak alleen sessies wanneer nodig
                .invalidSessionUrl("/login")  // Redirect naar login bij een ongeldige sessie

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
