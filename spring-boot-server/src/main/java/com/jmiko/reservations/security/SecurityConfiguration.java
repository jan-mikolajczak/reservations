package com.jmiko.reservations.security;

import com.jmiko.reservations.config.UserRole;
import com.jmiko.reservations.filter.JWTAuthenticationFilter;
import com.jmiko.reservations.filter.JWTAuthorizationFilter;
import com.jmiko.reservations.helper.JWTHelper;
import com.jmiko.reservations.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SecurityConfiguration {

    private final JWTHelper jwtHelper;


    public SecurityConfiguration(JWTHelper jwtHelper) {
        this.jwtHelper = jwtHelper;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/authenticate").permitAll()
                        .requestMatchers("/api/save").permitAll()
                        .requestMatchers("/refresh-token/**").permitAll()
                        .requestMatchers("/register-vendor").permitAll()
                        .requestMatchers("/get-vendor").permitAll()
                        .requestMatchers("/service").hasAnyAuthority(UserRole.MANAGER.name())
                        .requestMatchers("/vendor-employees").hasAuthority(UserRole.MANAGER.name())
                        .anyRequest()
                        .authenticated())
                .cors(withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.
                        sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilter(new JWTAuthenticationFilter(authenticationManager(http.getSharedObject(AuthenticationConfiguration.class)), jwtHelper))
                .addFilterBefore(new JWTAuthorizationFilter(jwtHelper), UsernamePasswordAuthenticationFilter.class)
//                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:8081"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Access-Control-Allow-Headers", "Access-Control-Allow-Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Origin", "Cache-Control", "Content-Type", "Authorization"));
        configuration.setAllowedMethods(Arrays.asList("DELETE", "GET", "POST", "PATCH", "PUT"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
