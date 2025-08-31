package com.InmoSoft.InmoSoft_Proyect.infraestructur.config;

import com.InmoSoft.InmoSoft_Proyect.infraestructur.security.JwtAuthEntryPoint;
import com.InmoSoft.InmoSoft_Proyect.infraestructur.security.JwtAuthenticationFilter;
import com.InmoSoft.InmoSoft_Proyect.service.AuthenticatedService;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private AuthenticatedService authenticatedService;

    private JwtAuthEntryPoint jwtAuthEntryPoint;

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    public SecurityConfig(AuthenticatedService authenticatedService, JwtAuthEntryPoint jwtAuthEntryPoint, JwtAuthenticationFilter jwtAuthenticationFilter) {
        this.authenticatedService = authenticatedService;
        this.jwtAuthEntryPoint = jwtAuthEntryPoint;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(authenticatedService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    //Todos los enpoints liberados sin autenticacion
    /*
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // TODAS las rutas son públicas
                );
        return http.build();
    }*/



    // enpoints de funciones que se permiten para todos

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorization -> authorization
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll() // ⬅️ Permitir preflight CORS
                        .requestMatchers("/api/login").permitAll()
                        .requestMatchers("/api/usuarios/registro").permitAll()
                        .requestMatchers("/api/usuarios/actualizar").permitAll()
                        .requestMatchers("/api/administradores/registro").permitAll()
                        .requestMatchers("/api/propietarios/guardar").permitAll()
                        .requestMatchers("/api/propiedades/crear").permitAll()
                        .requestMatchers("/api/propiedades/todas").permitAll()
                        .requestMatchers("/api/propiedades/actualizar").permitAll()

                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/documentacion-api/**"
                        ).permitAll()
                        .requestMatchers("/verificacion/token").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exceptionHandling -> exceptionHandling.authenticationEntryPoint(jwtAuthEntryPoint))
                .headers(AbstractHttpConfigurer::disable)
                .build();
    }

    @Bean
    public GeometryFactory geometryFactory() {
        return new GeometryFactory(new PrecisionModel(), 4326); // EPSG:4326
    }
}