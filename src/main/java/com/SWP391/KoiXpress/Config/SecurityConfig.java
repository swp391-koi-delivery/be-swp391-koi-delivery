package com.SWP391.KoiXpress.Config;


import com.SWP391.KoiXpress.Service.AuthenticationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    Filter filter;

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http)  throws Exception {
            return http
                    .cors().and()
                    .csrf(AbstractHttpConfigurer::disable)
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // Cho phép tất cả pre-flight requests
                            .requestMatchers("/**").permitAll()
                            .anyRequest().authenticated()
                    )
                    .userDetailsService(authenticationService)
                    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                    .build();
        }

    @Bean
    public SecurityFilterChain securityFilterChainGG(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers("/api/login/oauth2/success", "/swagger-ui/**", "/v3/api-docs/**").permitAll()  // Permit Swagger
                        .anyRequest().authenticated()
                )
                .oauth2Login(oauth2Login -> oauth2Login
                        .defaultSuccessUrl("/api/login/oauth2/success", true)  // On success, redirect to API endpoint
                        .failureUrl("/login?error=true")  // On failure, redirect to login error
                );
        return http.build();
    }
}
