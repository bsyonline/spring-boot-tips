package com.rolex.tips.config;

import com.rolex.tips.service.UserService;
import com.rolex.tips.service.impl.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    JwtService jwtService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
//                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                .ignoringAntMatchers("/login")
//                .and()
                .cors()
                .and()
                .addFilterBefore(new JwtLoginFilter("/login", authenticationManager(), userService, jwtService), UsernamePasswordAuthenticationFilter.class)
//                .addFilterBefore(new JwtFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/token", "/refresh", "/logout").permitAll()
                .anyRequest().access("@permissionServiceImpl.hasPermission(request, authentication)")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST"));
        corsConfiguration.applyPermitDefaultValues();
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
