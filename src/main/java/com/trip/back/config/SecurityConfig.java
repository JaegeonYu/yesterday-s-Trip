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

import com.spring.enjoytrip.account.AccountService;
import com.spring.enjoytrip.account.Role;
import com.spring.enjoytrip.security.Jwt;
import com.spring.enjoytrip.security.JwtAuthenticationProvider;
import com.spring.enjoytrip.security.JwtAuthenticationTokenFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	  private final Jwt jwt;

	  private final JwtTokenConfig jwtTokenConfig;
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public JwtAuthenticationProvider jwtAuthenticationProvider(Jwt jwt, AccountService accountService) {
		return new JwtAuthenticationProvider(jwt, accountService);
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
		http.csrf().disable().headers().disable().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers("/api/auth").permitAll()
				.antMatchers("/api/account/join").permitAll()
				.antMatchers("/api/**").hasRole(Role.USER.name()).and().formLogin().disable();
		
		http.addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();

	}

}
