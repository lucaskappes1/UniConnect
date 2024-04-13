package br.com.uniconnect.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.uniconnect.dtos.AuthenticationDTO;
import br.com.uniconnect.dtos.LoginResponseDTO;
import br.com.uniconnect.dtos.RegisterDTO;
import br.com.uniconnect.entities.User;
import br.com.uniconnect.infra.TokenService;
import br.com.uniconnect.repositories.UserRepository;

@RequestMapping("/auth")
@RestController
public class UserController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@PostMapping("/login")
	public ResponseEntity login(@RequestBody AuthenticationDTO data) {

		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());

		var auth = this.authenticationManager.authenticate(usernamePassword);
		
		var token = tokenService.generateToken((User) auth.getPrincipal());
		
		return ResponseEntity.ok(new LoginResponseDTO(token));

	}
	
	@PostMapping("/signup")
	public ResponseEntity register(@RequestBody RegisterDTO data) {
		if(this.userRepository.findByEmail(data.email()) != null) {
			return ResponseEntity.badRequest().build();
		} 
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(data.email(), encryptedPassword, data.role());
		
		this.userRepository.save(newUser);
		
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/userdata")
	public User getUserData(@RequestBody AuthenticationDTO data) {
		User s = (User) userRepository.findByEmail(data.email());
		s.setPassword(null);
		return s;
	}
}
