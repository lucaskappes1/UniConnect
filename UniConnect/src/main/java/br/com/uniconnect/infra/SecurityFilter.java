package br.com.uniconnect.infra;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.uniconnect.entities.User;
import br.com.uniconnect.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter{
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		Optional<User> user = null;
		if(token != null) {
			var userId = tokenService.validateToken(token);
			user = userRepository.findById(userId);
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.get().getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			request.setAttribute("user", user.get());
		}

		filterChain.doFilter(request, response);
	}
	
	
	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if(authHeader == null) {
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}
	

}
