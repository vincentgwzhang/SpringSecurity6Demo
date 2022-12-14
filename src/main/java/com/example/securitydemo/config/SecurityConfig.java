package com.example.securitydemo.config;

import com.example.securitydemo.config.security.handler.SecutiryAuthenticationFailureHandler;
import com.example.securitydemo.config.security.handler.SecutiryAuthenticationSuccessHandler;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final Log logger = LogFactory.getLog(this.getClass());

    @Autowired
    private SecutiryAuthenticationSuccessHandler successHandler;

    @Autowired
    private SecutiryAuthenticationFailureHandler failureHandler;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        String[] staticResources = new String[]{
                "/webjars/**",
                "/css/**",
                "/js/**",
        };

        return web -> web.ignoring().requestMatchers(staticResources);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        au -> au
                                .requestMatchers("/login").permitAll()
                                .anyRequest().authenticated()
                )
                .formLogin().loginPage("/login").successHandler(successHandler).failureHandler(failureHandler).permitAll()
                .and().httpBasic();
        return http.build();
    }

    @Bean
    protected InMemoryUserDetailsManager userDetailsService() {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("vincent").password(passwordEncoder().encode("1q2w3e4R")).roles("DEVELOPER").build());
        manager.createUser(User.withUsername("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build());
        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
