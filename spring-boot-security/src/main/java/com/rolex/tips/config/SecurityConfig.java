package com.rolex.tips.config;

import com.rolex.tips.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;
    @Autowired
    CustomAuthenticationFailureHandler customAuthenticationFailureHandler;
    @Autowired
    CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    @Autowired
    CustomLogoutSuccessHandler customLogoutSuccessHandler;
    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .rememberMe()
                .rememberMeParameter("remember-me")
                .rememberMeCookieName("remember-me-cookie")
                .tokenValiditySeconds(60 * 60 * 24)
                .tokenRepository(persistentTokenRepository())
                .and()
                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .successHandler(customAuthenticationSuccessHandler)
                .failureHandler(customAuthenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login.html")
                .logoutSuccessHandler(customLogoutSuccessHandler)
                .and()
                .authorizeRequests()
                .antMatchers("/login.html", "/logout").permitAll()
//                .antMatchers("/", "/orders", "/products").hasAnyAuthority("ROLE_user", "ROLE_admin")
//                .antMatchers("/sys/**").hasAnyRole("admin")
//                .and()
//                .authorizeRequests().anyRequest().authenticated()
                .anyRequest().access("@permissionServiceImpl.hasPermission(request, authentication)")
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
//                .invalidSessionUrl("/invalidSession")
                .sessionFixation()
                .migrateSession()
                .maximumSessions(1)
                .maxSessionsPreventsLogin(true)
                .expiredSessionStrategy(new CustomExpiredSessionStrategy());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*auth.inMemoryAuthentication()
                .withUser("John")
                .password(passwordEncoder().encode("123"))
                .roles("user")
                .and()
                .withUser("admin")
                .password(passwordEncoder().encode("123"))
                .roles("admin")
                .and()
                .passwordEncoder(passwordEncoder());*/
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/font/**");
    }

    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }
}
