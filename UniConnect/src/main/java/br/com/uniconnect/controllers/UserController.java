package br.com.uniconnect.controllers;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.uniconnect.dtos.AuthenticationDTO;
import br.com.uniconnect.dtos.LoginResponseDTO;
import br.com.uniconnect.dtos.RegisterDTO;
import br.com.uniconnect.dtos.UserDataDTO;
import br.com.uniconnect.dtos.UserIdDTO;
import br.com.uniconnect.entities.User;
import br.com.uniconnect.infra.TokenService;
import br.com.uniconnect.repositories.UserRepository;
import br.com.uniconnect.services.UserService;

@RequestMapping("/auth")
@RestController
public class UserController {

	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired 
	private UserService userService;

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
	public ResponseEntity getUserData(@RequestHeader("Authorization") String token) throws IOException {
		String email = tokenService.validateToken(token);
		User s = (User) userRepository.findByEmail(email);
		return ResponseEntity.ok(new UserDataDTO(
				s.getId(),
				s.getEmail(),
				s.getName(),
				s.getTelefone(),
				s.getLogradouro(),
				s.getNumero(),
				s.getBairro(),
				s.getCidade(),
				s.getEstado(),
				s.getCurso(),
				userService.getImage(s.getId()).getContentAsByteArray()));
	}
	
	@PostMapping("/profilepicture")
	public ResponseEntity saveProfilePicture(@RequestParam("file") MultipartFile file, @RequestParam Long userId) {
		userService.saveImage(file, userId);
		return ResponseEntity.ok().build();
	}
}
