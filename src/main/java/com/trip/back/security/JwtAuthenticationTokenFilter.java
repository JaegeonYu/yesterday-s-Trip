package com.trip.back.security;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.GenericFilterBean;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.trip.back.security.Jwt.Claims;



public class JwtAuthenticationTokenFilter extends GenericFilterBean{
	
	 private static final Pattern BEARER = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);

	  private final Logger log = LoggerFactory.getLogger(getClass());

	  private final String headerKey;
	  private final Jwt jwt;
	  
	  
	public JwtAuthenticationTokenFilter(String headerKey, Jwt jwt) {
		super();
		this.headerKey = headerKey;
		this.jwt = jwt;
	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		if(SecurityContextHolder.getContext().getAuthentication() == null) {
			String authorizationToken = obtainAuthorizationToken(req);
			
			if(authorizationToken != null) {
				try {
					Jwt.Claims claims = verify(authorizationToken);
					
					if(canRefresh(claims, 300 * 1000)) {
						System.out.println("canRefresh : " + jwt.refreshToken(authorizationToken));
						String refreshedToken = jwt.refreshToken(authorizationToken);
						res.setHeader(headerKey, refreshedToken);
					}
					
					Long userKey = claims.userKey;
					String email = claims.email;
					String nickname = claims.nickname;
					List<GrantedAuthority> authorities = obtainAuthorities(claims);
					
					if(userKey != null && email != null && authorities.size() >0) {
						JwtAuthenticationToken authentication = new JwtAuthenticationToken(new JwtAuthentication(userKey, nickname, email), null, authorities);
						log.info("auth : {}" , authentication);
						authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
						SecurityContextHolder.getContext().setAuthentication(authentication);
						log.info("finish : {}" , authentication);
						
					}
				}catch(Exception e) {
					log.warn("Jwt Process failed : {}", e.getMessage());
				}
			}
		}else {
			 log.debug("SecurityContextHolder not populated with security token, as it already contained: '{}'",
				        SecurityContextHolder.getContext().getAuthentication());
		}
		
		chain.doFilter(req, res);
		
	}


	private List<GrantedAuthority> obtainAuthorities(Claims claims) {
		String[] roles = claims.roles;
		if(roles == null || roles.length == 0)return Collections.emptyList();
		return Arrays.stream(roles).map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
	}


	private boolean canRefresh(Claims claims, long refreshRangeMillis) {
		long exp = claims.exp();
		if(exp > 0) {
			long remain = exp - System.currentTimeMillis();
			return remain < refreshRangeMillis;
		}
		return false;
	}


	private Claims verify(String authorizationToken) {
		return jwt.verify(authorizationToken);
	}


	private String obtainAuthorizationToken(HttpServletRequest req) {
		String token = req.getHeader(headerKey);
		if(token != null) {
			try {
				token = URLDecoder.decode(token, "UTF-8");
				String[] tokens = token.split(" ");
				if(tokens.length == 2) {
					String scheme = tokens[0];
					String credentails = tokens[1];
					return BEARER.matcher(scheme).matches() ? credentails : null;
				}
			}catch(Exception e) {
				log.error("Jwt ObtainAuthorization Process error");
			}
		}
		return null;
	}

}
