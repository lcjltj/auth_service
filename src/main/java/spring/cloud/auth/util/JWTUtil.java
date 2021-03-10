package spring.cloud.auth.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import spring.cloud.auth.entity.User;

@Component
public class JWTUtil {

	@Value("${jwt.secret.key}")
	private String secretKey;
	@Value("${jwt.secret.accesstoken_timeout}")
	private String accesstokenTimeout;

	private Key key;

	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
	}

	public Claims getAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
	}

	public String getUserNumber(String token) {
		return getAllClaims(token).getSubject();
	}

	public Date getExpirationDate(String token) {
		return getAllClaims(token).getExpiration();
	}

	private Boolean isTokenExpired(String token) {
		boolean invalid = false;

		final Date expiration = getExpirationDate(token);
		invalid = expiration.before(new Date());

		if (invalid) {
			throw new ExpiredJwtException(null, null, null);
		}
		return true;

	}

	public String generateToken(User user) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("email", user.getEmail());
		claims.put("name", user.getName());
		claims.put("nickName", user.getNickName());
		claims.put("role", user.getRole());
		
		return doGenerateToken(claims, user.getNumber());
	}

	private String doGenerateToken(Map<String, Object> claims, long number) {
		Long expirationTimeLong = Long.parseLong(accesstokenTimeout); // 초

		final Date createdDate = new Date();
		final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong * 1000);

		return Jwts.builder()
				.setClaims(claims)
				.setSubject(Long.toString(number))
				.setIssuedAt(createdDate)
				.setExpiration(expirationDate)
				.signWith(key)
				.compact();
	}

	public Boolean validateToken(String token) {
		return !isTokenExpired(token);
	}

}