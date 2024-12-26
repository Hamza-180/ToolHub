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
            .authorizeRequests(authz -> authz
                .requestMatchers("/login", "/register","/**/*.css").permitAll()  // Zorg ervoor dat login en register toegankelijk zijn zonder authenticatie
                .requestMatchers("/api/reservations/**","/**/*.css").authenticated()  // Alleen geauthenticeerde gebruikers kunnen reserveringen openen
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")  // Aangepaste inlogpagina
                .permitAll()
                .defaultSuccessUrl("/dashboard", true)  // Redirect naar dashboard na succesvolle login
            )
            
            .csrf(csrf -> csrf
                .ignoringRequestMatchers("/api/auth/login", "/api/auth/register", "/api/**", "/logout")
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
                       .requestMatchers("/**", "/js/**", "/images/**");
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
