package be.ehb.toolhub.config;

import be.ehb.toolhub.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
                .requestMatchers("/login", "/register", "/css/**", "/js/**").permitAll()  // Zorg ervoor dat login, register en statische bestanden toegankelijk zijn zonder authenticatie
                .requestMatchers("/api/auth/login", "/api/auth/register", "/api/**").permitAll()  // Toestaan voor API-login en registratie
                .requestMatchers("/api/reservations/**").authenticated()  // Alleen geauthenticeerde gebruikers kunnen reserveringen openen
                .anyRequest().authenticated()  // Alle andere routes vereisen authenticatie
            )
            .formLogin(form -> form
                .loginPage("/login")  // Specificeer de loginpagina
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect naar dashboard na succesvolle login
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Specificeer logout URL
                .logoutSuccessUrl("/login")  // Redirect naar login na logout
                .permitAll()
            )

                  .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/login", "/register", "/logout","/api/**")
                )


            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)  // Maak alleen sessies wanneer nodig
                .invalidSessionUrl("/login")  // Redirect naar login bij een ongeldige sessie
                .maximumSessions(1)  // Zorg ervoor dat slechts 1 sessie per gebruiker actief is
                .expiredUrl("/login?expired")  // Redirect naar een speciale pagina als de sessie is verlopen
            );

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                       .requestMatchers("/js/**", "/images/**", "/css/**");  // Zorg ervoor dat statische bestanden worden genegeerd
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);  // Gebruik een BCryptPasswordEncoder met een werkfactor van 12
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        return authBuilder.build();
    }
}
