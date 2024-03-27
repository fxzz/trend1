package com.trend.security;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final LoginSuccessHandler loginSuccessHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests((authorizeRequests) ->
                authorizeRequests
                        .requestMatchers(HttpMethod.GET,"/", "/calendar", "/contact", "/login", "/timeline/**", "/sign-up", "/posting", "/view/**", "posting-list" ,"/detail-calendar", "/search", "/posting-user", "/excel", "/category", "/product-list", "/product-detail/*", "/product-cart" ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/sign-up", "/product-cart-add" ,"/product-cart-del", "/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/product/new" ,"/chat-room").authenticated()
                        .requestMatchers(HttpMethod.POST, "/posting", "/profile", "/profile-image", "/comment", "/posting-like" ,"/product-image", "/product/new", "/room-create", "/contact").authenticated()
                        .requestMatchers("/error/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated())

                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .successHandler(loginSuccessHandler)
                        .permitAll())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        )

                .logout(httpSecurityLogoutConfigurer ->
                        httpSecurityLogoutConfigurer.logoutSuccessUrl("/"))



        ;

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
                //.requestMatchers("/error")
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



}
