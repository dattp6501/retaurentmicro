package com.dattp.authservice.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.dattp.authservice.filter.JwtAuthFilter;
import com.dattp.authservice.filter.JwtAuthenticationFilter;
import com.dattp.authservice.service.UserService;

@Configuration //(proxyBeanMethods = false)
// @ConditionalOnDefaultWebSecurity
@EnableWebSecurity
public class SecurityConfig{

    // @Autowired
    // private JwtAuthFilter JwtAuthFilter;

    @Autowired
    public UserService userService;

    // private final static List<UserDetails> APPLICATION_USERS = Arrays.asList(
    //     new User("dattp","dattp",Collections.singleton(new SimpleGrantedAuthority("ADMIN"))),
    //     new User("user","user",Collections.singleton(new SimpleGrantedAuthority("USER")))
    // );
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService)
            .passwordEncoder(passwordEncoder());
    }
    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    // @Bean
    // public InMemoryUserDetailsManager userDetailsService() {
    //     UserDetails user = User.builder()
    //         .username("dattp")
    //         .password("dattp")
    //         .roles("ADMIN")
    //         .build();
    //     return new InMemoryUserDetailsManager(user);
    // }



    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // http.csrf().disable()
        // .authorizeRequests()
        // .antMatchers("/**/auth/**")
        // .permitAll()
        // .anyRequest()
        // .authenticated();
        // http.authorizeHttpRequests(requests -> requests
        //         .anyRequest().authenticated()
        // );

        
    //     http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //     // http.authenticationProvider((authenticationProvider()));

    //     http.addFilterBefore(JwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
    //     // http.httpBasic();
    //     // http.formLogin(
    //     //     login -> login 
    //     //     .defaultSuccessUrl("/api/auth/home")
    //     //     .permitAll()
    //     // );
    //     // http.logout(logout -> logout.permitAll());
    //     return http.build();




        http
        .cors() // Ngăn chặn request từ một domain khác
            .and()
        .authorizeRequests()
            .antMatchers("/api/login").permitAll() // Cho phép tất cả mọi người truy cập vào địa chỉ này
            .anyRequest().authenticated(); // Tất cả các request khác đều cần phải xác thực mới được truy cập

        // Thêm một lớp Filter kiểm tra jwt
        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    // @Bean
    // public AuthenticationProvider authenticationProvider() {
    //     final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    //     authenticationProvider.setUserDetailsService(UserDetailsService());
    //     authenticationProvider.setPasswordEncoder(passwordEncoder());
    //     return authenticationProvider;
    // }

    

    // @Bean
    // public UserDetailsService UserDetailsService(){
    //     return new UserDetailsService() {
    //         @Override
    //         public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    //             return APPLICATION_USERS.stream()
    //             .filter(u -> u.getUsername().equals(email))
    //             .findFirst()
    //             .orElseThrow(()->new UsernameNotFoundException("Không tìm thấy người dùng này"));
    //         }
            
    //     };
    // }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception{
        return config.getAuthenticationManager();
    }


    // // @Bean
    // // public WebSecurityCustomizer webSecurityCustomizer() {
    // //     return (web) -> web.ignoring().antMatchers("/ignore1", "/ignore2");
    // // }

}
