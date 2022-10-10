package com.mirceanealcos.confruntarea.security;

import com.mirceanealcos.confruntarea.security.filter.CustomAuthFilter;
import com.mirceanealcos.confruntarea.security.filter.CustomAuthorizationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.http.HttpMethod.GET;


/**
 * This class is used to configure the security aspect of the application.
 * This includes securing certain endpoints based on the role of the user that is logged in and adding different custom filters.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    SecurityConfig(UserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }


    /**
     * This method builds the authentication manager using custom fields
     * @param auth the authentication manager builder
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }


    /**
     * This method configures the different aspects of security such as CSRF and CORS,
     * and what endpoints are accessible to everyone and which need authentication or certain roles to be accessed.
     * @param http The HTTP security configurer
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers(permittedPaths).permitAll();
        http.authorizeRequests().antMatchers("/v3/api-docs/**", "/configuration/ui", "/swagger-resources/**", "/configuration/**", "/swagger-ui.html", "/webjars/**").permitAll();
        http.authorizeRequests().antMatchers(adminPaths).hasAnyAuthority("admin");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(new CustomAuthFilter(authenticationManagerBean()));
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "OPTIONS", "PUT", "DELETE")
                .maxAge(3600);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private static final String[] permittedPaths = {
            "/login",
            "/api/users/refresh",
            "/api/users/username/**",
            "/api/users/email/**",
            "/api/users/user-register",
            "/api/users/purchase/**",
            "/api/users/update/**",
            "/api/users/page/**",
            "/api/champions",
            "/api/champions/page/**",
            "/api/items",
            "/api/items/type/**",
            "/api/items/page/**",
            "/api/abilities",
            "/api/abilities/champion_name/**",
            "/api/abilities/page/**",
            "/api/monsters",
            "/api/monsters/page/**",
            "/api/item-ownerships/decrease/**",
            "/api/item-ownerships/increase/**",
            "/api/champion-ownerships/",
            "/api/champion-ownerships/user-name/**",
            "/api/champion-ownerships/champion-name/**",
            "/api/champion-ownerships/link-user/**",
            "/mail/**",
            "/reset-password/**",
            "/email-validation"
    };

    private static final String[] adminPaths = {
            "/api/users/**",
            "/api/champions/**",
            "/api/abilities/**",
            "/api/items/**",
            "/api/monsters/**",
            "/export/excel/**",
            "/import/excel/**"
    };
}
