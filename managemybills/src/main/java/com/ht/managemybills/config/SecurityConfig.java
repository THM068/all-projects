package com.ht.managemybills.config;

import com.ht.managemybills.userDetailService.JpaUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final String H2_CONSOLE = "/h2-console/**";
  private final JpaUserDetailsService jpaUserDetailsService;

  public SecurityConfig(JpaUserDetailsService jpaUserDetailsService) {
    this.jpaUserDetailsService = jpaUserDetailsService;
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .csrf(csrf -> csrf.ignoringRequestMatchers(H2_CONSOLE))
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(H2_CONSOLE).permitAll()
          .anyRequest().authenticated();
        })
        .headers(headers -> headers.frameOptions(Customizer.withDefaults()).disable())
        .userDetailsService(jpaUserDetailsService)
        .formLogin(Customizer.withDefaults())
        .build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
