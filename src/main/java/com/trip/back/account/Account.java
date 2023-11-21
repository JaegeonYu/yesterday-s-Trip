package com.trip.back.account;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.trip.back.security.Jwt;

import jdk.jfr.StackTrace;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@ToString
@Setter
public class Account {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime emailAt;
    @Setter
    private List<Roles> roles;
    

    @Builder
    public Account(String email, String nickname, String password, LocalDateTime emailAt) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
        this.emailAt = emailAt;
    }
    
    public void passEncode(String encodePassword) {
    	this.password = encodePassword;
    }
    

    public void login(PasswordEncoder passwordEncoder, String credentials) {
      if (!passwordEncoder.matches(credentials, password))
        throw new IllegalArgumentException("Bad credential");
    }

	public String newApiToken(Jwt jwt, String[] roles) {
		Jwt.Claims claims = Jwt.Claims.of(id, email, nickname, roles);
	    return jwt.newToken(claims);
	}
	
	public String newRefreshApiToken(Jwt jwt, String[] roles) {
		Jwt.Claims claims = Jwt.Claims.of(id, email, nickname, roles);
	    return jwt.newRefreshToken(claims);
	}
	
	public void afterLogin() {
		this.password = null;
	}
	
	public boolean canSendEmail() {
		return this.emailAt.isBefore(LocalDateTime.now().minusHours(1));
	}
}
