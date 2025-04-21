package com.entreprise.msuser.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final JwtAuthConverter jwtAuthConverter;

    public SecurityConfig(JwtAuthConverter jwtAuthConverter){
        this.jwtAuthConverter=jwtAuthConverter;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        return httpSecurity
                .cors(Customizer.withDefaults())
                .sessionManagement(sm->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .headers(h -> h.frameOptions(fo -> fo.disable()))
                .csrf(csrf->csrf.disable())
               .authorizeHttpRequests((ar->ar.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html","/ndf/**","/user/**").permitAll()))
                .authorizeHttpRequests(ar -> ar.requestMatchers("/auth/login/**").permitAll())

                //.authorizeHttpRequests(ar->ar.requestMatchers("/user/**").hasAuthority("AdminSysteme"))
               // .authorizeHttpRequests(ar->ar.requestMatchers("/user/{id}","/user/update/{id}","/user").hasAuthority("AdminFinance"))
                //.authorizeHttpRequests(ar->ar.requestMatchers("/user/{id}","/user/update/**").hasAuthority("Manager"))
               //.authorizeHttpRequests(ar->ar.requestMatchers("/user/{id}","/user/update/**").hasAuthority("Employee"))
                .authorizeHttpRequests(ar->ar.anyRequest().authenticated())
                .oauth2ResourceServer(o2->o2.jwt(jwt->jwt.jwtAuthenticationConverter(jwtAuthConverter)))
                .build();

    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
