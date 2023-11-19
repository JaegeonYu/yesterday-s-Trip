package com.trip.back.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.trip.back.account.AccountService;
import com.trip.back.account.Role;
import com.trip.back.auth.TokenMapper;
import com.trip.back.security.EntryPointUnauthorizedHandler;
import com.trip.back.security.Jwt;
import com.trip.back.security.JwtAccessDeniedHandler;
import com.trip.back.security.JwtAuthenticationProvider;
import com.trip.back.security.JwtAuthenticationTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final Jwt jwt;

	private final JwtTokenConfig jwtTokenConfig;
	
	  private final JwtAccessDeniedHandler accessDeniedHandler;

	  private final EntryPointUnauthorizedHandler unauthorizedHandler;
	  private final TokenMapper tokenRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, AccountService accountService, TokenMapper tokenRepository) {
		return new JwtAuthenticationProvider(jwt, accountService, tokenRepository);
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
		return new JwtAuthenticationTokenFilter(jwtTokenConfig.getHeader(), jwt);
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable().headers().disable()
		.exceptionHandling()
		.accessDeniedHandler(accessDeniedHandler)
		.authenticationEntryPoint(unauthorizedHandler)
		.and()
		.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/auth/refreshToken").permitAll()
				.antMatchers("/api/auth").permitAll()
				.antMatchers("/api/account/**").permitAll()
				.antMatchers("/api/parse/**").permitAll()
				.antMatchers("/api/attraction/**").permitAll()
				.antMatchers("/api/review/list/**").permitAll()
				.antMatchers("/api/region/**").permitAll()
				.antMatchers("/api/s3/**").permitAll()
				.antMatchers("/api/**").hasRole(Role.USER.name()).and().formLogin().disable();
		
		http.cors();

		http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}
	
	 @Bean
	    public CorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration configuration = new CorsConfiguration();
	        configuration.addAllowedOrigin("*"); // 허용할 Origin (모든 Origin 허용)
	        configuration.addAllowedMethod("*"); // 모든 HTTP 메소드 허용
	        configuration.addAllowedHeader("*"); // 모든 헤더 허용

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", configuration);
	        
	        return source;
	    }

}
