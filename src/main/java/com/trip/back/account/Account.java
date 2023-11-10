package com.trip.back.account;

import lombok.*;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.trip.back.security.Jwt;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@ToString
public class Account {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private boolean emailVerified;
    private String emailCheckToken;
    private LocalDateTime joinedAt;
    private String bio;

    @Builder
    public Account(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
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
	
	public void afterLogin() {
		this.password = null;
	}

}
