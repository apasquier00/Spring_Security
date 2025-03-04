package com.example.springsecurity.config;

import com.example.springsecurity.service.UserService;
import com.example.springsecurity.util.JwtFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    @Autowired
    private UserService userService;
    public SecurityConfig(UserDetailsService userDetailsService,
                          AuthenticationConfiguration authenticationConfiguration, JwtFilter
                                  jwtFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationConfiguration = authenticationConfiguration;
        this.jwtFilter = jwtFilter;
    }
    
    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;
    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/**").permitAll()

                        .requestMatchers("/users/**").hasRole("ADMIN")

                        .requestMatchers("/test/admin").hasRole("ADMIN")
                        .requestMatchers("/test/user").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtFilter,
                        UsernamePasswordAuthenticationFilter.class)
                .build();
    }



    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
// return new BCryptPasswordEncoder();
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    // Fake users in memory
//    @Bean
//    public static UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager userDetailsManager = new InMemoryUserDetailsManager();
////        Stream<UserDto> users = userService.getAllUsers();
////        users.forEach(user -> {
////            System.out.printf(user.toString());
////            userDetailsManager.createUser(
////                    User.withDefaultPasswordEncoder()
////                            .username(user.username())
////                            .password(user.password())
////                            .roles(String.valueOf(user.role()))
////                            .build()
////            );
////        });
////        return userDetailsManager;
//
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("admin")
//                        .password("admin123")
//                        .roles("ADMIN")
//                        .build()
//        );
//        userDetailsManager.createUser(
//                User.withDefaultPasswordEncoder()
//                        .username("user")
//                        .password("user123")
//                        .roles("USER")
//                        .build()
//        );
//        return userDetailsManager;
//    }

}