package com.ratseno.util;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil implements Serializable{

	private static final long serialVersionUID = 1148609719250145022L;
	
	private static final long JWT_VALIDITY = 5 * 60 * 60;
	
	@Value("${jwt.secret}")
	private String secret;
	
	//Payload 부분에는 토큰에 담을 정보가 들어있다. 
	//여기에 담는 정보의 한 ‘조각’ 을 클레임(claim) 이라고 부르고, 
	//이는 key / value 의 한 쌍으로 이뤄져있다. 
	//토큰에는 여러개의 클레임 들을 넣을 수 있다.
	
	//프로퍼티의 시크릿키를 이용하여 만들어진 JWS를
	//시크릿키를 이용하여 파싱하고,
	//페이로드의 Claims를 리턴한다.
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser()
				.setSigningKey(secret)
				.parseClaimsJws(token)
				.getBody();
	}
	
	//jwt토큰에서 정보를 획득한다.
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver){
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}
	
	//JWT 토큰에서 username을 획득한다.
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
	
	//JWT 토큰에서 만료날짜 획득
	private Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}
	
	//JWT 토큰이 만료되었는지 확인
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}
	
	//JWT 토큰 생성
	private String doGenerateToken(Map<String, Object>claims, String subject) {
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(subject)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_VALIDITY * 1000))
				.signWith(SignatureAlgorithm.HS512, secret)
				.compact();
	}
	
	//사용자를 위한 JWT 토큰 생성
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<String, Object>();
		return doGenerateToken(claims, userDetails.getUsername());
	}

	//JWT 토큰이 유효한지 체크
	//-토큰의 subject와 유저의 username이 동일한지
	//-토큰이 만료날짜가 유효한지
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
