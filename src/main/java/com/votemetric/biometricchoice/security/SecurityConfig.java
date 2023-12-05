package com.votemetric.biometricchoice.security;

import com.votemetric.biometricchoice.interfaces.IAdminService;
import com.votemetric.biometricchoice.mapper.Mapper;
import com.votemetric.biometricchoice.security.filter.AuthenticationFilter;
import com.votemetric.biometricchoice.security.filter.ExceptionHandlerFilter;
import com.votemetric.biometricchoice.security.filter.JWTAuthorizationFilter;
import com.votemetric.biometricchoice.security.manager.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;
    private final Mapper mapper;

    private final IAdminService adminService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/authenticate");
        http
                .headers().frameOptions().disable()
                .and()
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests().antMatchers().permitAll()
                .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll()
                .antMatchers("/authenticate").permitAll()
                .antMatchers("/mobile-users/login").permitAll()
                .antMatchers("/mobile-users/register").permitAll()
                .antMatchers("/h2/**").permitAll()
                .antMatchers("/v3/**").permitAll() //permit all methods starting with "/v3"
                .antMatchers("/swagger-ui/**").permitAll() //permit all methods starting with "/v3"
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterBefore(new JWTAuthorizationFilter(adminService), AuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }


}
