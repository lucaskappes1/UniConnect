package br.com.uniconnect.infra;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.uniconnect.entities.User;
import br.com.uniconnect.exceptions.InvalidTokenException;

@Service
public class TokenService {
	
	@Value("${api.security.token.secret}")
	private String secret;
	
	private Set<String> blacklistedTokens = new HashSet<>();

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			String token = JWT.create()
						.withIssuer("uniconnect")
						.withSubject(user.getEmail())
						.withClaim("userId", user.getId())
						.withExpiresAt(generateExpirationDate())
						.sign(algorithm);
			return token;
		}catch(JWTCreationException e) {
			throw new RuntimeException("Error while generating token");
		}
		
	}
	
	public Long validateToken(String token) {
		
		if(blacklistedTokens.contains(token)) {
			throw new RuntimeException("Erro ao validar token");
		}
		
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			return JWT.require(algorithm)
					.withIssuer("uniconnect")
					.build()
					.verify(token)
					.getClaim("userId")
					.asLong();
		} catch(JWTVerificationException e) {
			throw new InvalidTokenException("Token inválido!");
		}
	}
	
	public boolean deactivateToken(String token) {
		blacklistedTokens.add(token);
		return true;
	}
	
	@Scheduled(fixedRate = 10800000)
	private void validateExpirationDateToken() {
		for(String s : blacklistedTokens) {
			try {
				Algorithm algorithm = Algorithm.HMAC256(secret);
				JWT.require(algorithm)
					.withIssuer("uniconnect")
					.build()
					.verify(s);
			} catch(JWTVerificationException e) {
				blacklistedTokens.remove(s);
			}
		}
	}
	
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
	
}
