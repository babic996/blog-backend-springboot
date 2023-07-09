package com.project.blog.configuration;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.project.blog.model.User;
import com.project.blog.repository.UserRepository;
import com.project.blog.repository.UserRoleRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	@Value("${jwt.secret}")
	private String SECRET_KEY;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	public String extractTenantName(String token) {
		return extractAllClaims(token).get("tenantName", String.class);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}

	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public String generateToken(UserDetails userDetails) throws Exception {
		Map<String, Object> claims = new HashMap<>();
		User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
				() -> new Exception("Nije pronađen korisnik (username: " + userDetails.getUsername() + ")"));
		List<String> userRoles = userRoleRepository.findAllByUser(user).stream()
				.map(e -> e.getRole().getName().substring(5)).collect(Collectors.toList());
		claims.put("userId", user.getUserId());
		claims.put("firstName", user.getFirstName());
		claims.put("lastName", user.getLastName());
		claims.put("role", userRoles);
		return createToken(claims, userDetails.getUsername());
	}

	private String createToken(Map<String, Object> claims, String subject) {

		// @formatter:off
		
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				// .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.setExpiration(setTokenExpirationDate(480))
				.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
		
		// @formatter:on
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

	private Date setTokenExpirationDate(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, minutes);

		return calendar.getTime();
	}
}
