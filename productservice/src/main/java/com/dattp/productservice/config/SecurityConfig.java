package com.dattp.productservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true,securedEnabled=true,jsr250Enabled=true)
public class SecurityConfig{
    @Autowired
    private JWTAuthenticationFilter jwtAuthenticationFilter;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.csrf(csrf -> csrf.disable());
        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.authorizeHttpRequests(auth -> auth
                // .antMatchers("/**").permitAll()
                .antMatchers("/h2-console/**").hasAuthority("ROLE_ADMIN")
                .antMatchers(GlobalConfig.pathPublic).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            )
            .headers(headers -> headers.frameOptions().disable())
            .csrf(csrf -> csrf
                .ignoringAntMatchers("/**")
            );
        return http.build();
    }
}