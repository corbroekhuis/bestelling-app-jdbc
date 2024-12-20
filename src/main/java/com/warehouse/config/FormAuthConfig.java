package com.warehouse.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class FormAuthConfig {

    private volatile boolean running;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 =
                User.builder()
                        .username("user")
                        .password(passwordEncoder().encode("user"))
                        .roles("USER")
                        .build();
        UserDetails user2 =
                User.builder()
                        .username("admin")
                        .password(passwordEncoder().encode("admin"))
                        .roles("USER", "ADMIN")
                        .build();
        return new InMemoryUserDetailsManager(user1, user2);
    }

    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.headers(header -> header.frameOptions(options -> options.sameOrigin()));
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/"
                                , "/h2-console/**"
                                , "/v3/api-docs"
                                , "/configuration/ui"
                                , "/swagger-resources/**"
                                , "/configuration/security"
                                , "/swagger-ui/**"
                                , "/swagger-ui.html"
                                , "/webjars/**"
                        ).permitAll()
                        .requestMatchers("/order").authenticated()
//                        .requestMatchers("/order").hasAnyAuthority("USER", "ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin( withDefaults())
                .logout( withDefaults());

        return http.build();
    }
}
