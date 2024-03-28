package com.openclassrooms.mddapi.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.crypto.spec.SecretKeySpec;


@Configuration
public class SpringSecurityConfig {

    private final CorsConfigurationSource corsConfigurationSource;

    public SpringSecurityConfig(CorsConfigurationSource corsConfigurationSource) {
        this.corsConfigurationSource = corsConfigurationSource;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors(cors -> cors.configurationSource(corsConfigurationSource));                                  // Configuration des règles CORS avec la source fournie.
        http.csrf(csrf -> csrf.disable())                                                                      // Désactivation de la protection CSRF
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // Définit la politique de création de session à 'STATELESS' pour ne pas conserver d'état côté serveur.
                .authorizeHttpRequests(auth -> auth                                                            // Configuration des autorisations de requêtes : quelles requêtes sont autorisées ou nécessitent une authentification.
                        .requestMatchers("/api/auth/register",
                                "/api/auth/login",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/images/**").permitAll()   // Permettre à tout le monde d'accéder aux endpoints
                        .anyRequest().authenticated());                                                        // Toutes les autres requêtes doivent être authentifiées.
      //  http.oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder())));                     // Configuration pour utiliser les tokens JWT comme moyen d'authentification OAuth2.
        return http.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}