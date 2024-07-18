package com.smartHomeHub.musicservice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Configuration
@Slf4j
@EnableWebSecurity
@EnableMethodSecurity(jsr250Enabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/unauthenticated", "/oauth2/**", "/login/**").permitAll()
                        .anyRequest().authenticated())

                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwtConfigurer -> jwtConfigurer
                                .jwtAuthenticationConverter(jwt -> {
                                    Map<String, Object> resourceAccess = jwt.getClaim("resource_access");
                                    @SuppressWarnings("unchecked")
                                    Map<String, Collection<String>> resource = (Map<String, Collection<String>>)
                                            resourceAccess.get("sprinkler-service");
                                    Collection<String> roles = resource.get("roles");
                                    log.info("Roles assumed are: {}", roles);
                                    List<SimpleGrantedAuthority> grantedAuthorities = roles
                                            .stream()
                                            .map(SimpleGrantedAuthority::new)
                                            .toList();
                                    return new JwtAuthenticationToken(jwt, grantedAuthorities);
                                })))
                .build();
    }

    @Bean
    public GrantedAuthorityDefaults grantedAuthorityDefaults() {
        return new GrantedAuthorityDefaults("");
    }
}

