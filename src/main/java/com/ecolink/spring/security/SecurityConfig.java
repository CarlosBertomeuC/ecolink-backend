package com.ecolink.spring.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.ecolink.spring.security.jwt.JwtAuthorizationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAuthorizationFilter jwtAuthorizationFilter;
	private final PasswordEncoder passwordEncoder;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.exceptionHandling(ex -> ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(auth -> auth
						.requestMatchers("/api/order/**", "/api/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**",
								"/api/admin/**","/api/subscriptions/**", "/api/product/**", "/api/mission/**", "/api/comment/**", "/api/like/**", "/api/category/**", "/api/proposal/**", "/api/verify/**","/api/profile/**", "/api/challenge/**", "/api/paypal/**", "/api/post/**")
						.permitAll()
						.requestMatchers(HttpMethod.GET, "/api/startup/**",
								"/api/ods/**", "/api/client/**", "/api/company/**", "/api/challenge/**","/api/subscriptions/**",
								"/api/image/**")
						.permitAll()

						.requestMatchers("/api/company").hasAuthority("ROLE_STARTUP")
						// .requestMatchers(HttpMethod.GET, "/api/mission").hasAuthority("ROLE_CLIENT")
						// .requestMatchers("/api/admin/**").hasAnyAuthority("ROLE_ADMIN")
						// .requestMatchers(HttpMethod.GET, "/api/challenge/**")
						// .hasAnyAuthority("ROLE_COMPANY", "ROLE_STARTUP")

						.anyRequest().authenticated())
				.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
				.csrf(csrf -> csrf.disable())
				.headers(headers -> headers
						.frameOptions(frameOptions -> frameOptions.disable()))
				.logout(logout -> logout
						.logoutUrl("/logout")
						.logoutSuccessUrl("/public")
						.invalidateHttpSession(true)
						.permitAll());
		return http.build();
	}

	@Bean
	protected AuthenticationManager authenticationManager(UserDetailsService userDetailsService) {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder);
		authProvider.setUserDetailsService(userDetailsService);
		return new ProviderManager(authProvider);
	}
}