package com.trip.back.account;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.trip.back.security.Jwt;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
public class Account {
    private Long id;
    private String email;
    private String nickname;
    private String password;
    private LocalDateTime emailAt;
    @Setter
    private List<Roles> roles;
    

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

	@Builder
	public Account(Long id, String email, String nickname, String password, LocalDateTime emailAt, List<Roles> roles) {
		super();
		this.id = id;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.emailAt = emailAt;
		this.roles = roles;
	}
}
