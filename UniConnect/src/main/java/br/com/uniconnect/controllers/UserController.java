package br.com.uniconnect.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import br.com.uniconnect.dtos.UserResponseDTO;
import br.com.uniconnect.entities.Enrollment;
import br.com.uniconnect.entities.User;
import br.com.uniconnect.infra.TokenService;
import br.com.uniconnect.repositories.EnrollmentRepository;
import br.com.uniconnect.repositories.UserRepository;
import br.com.uniconnect.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
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
	public ResponseEntity login(@RequestBody AuthenticationDTO data) throws IOException {
		var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User) auth.getPrincipal());
		User user = (User) userRepository.findByEmail(data.email());
		
		
		System.out.println(user.getPathProfilePicture());
		if(user.getPathProfilePicture() != null) {
			
			return ResponseEntity.ok(
					new LoginResponseDTO(token, new UserResponseDTO(
							user.getName(),
							user.getEmail(), 
							user.getId(),
							user.getPhone(),
							user.getAdress(),
							user.getCity(),
							user.getStateId(),
							userService.getImage(user.getId()).getContentAsByteArray()
							)));
		} else {
			return ResponseEntity.ok(
					new LoginResponseDTO(token, new UserResponseDTO(
							user.getName(),
							user.getEmail(), 
							user.getId(),
							user.getPhone(),
							user.getAdress(),
							user.getCity(),
							user.getStateId(),
							null
							)));
			}
	}
	
	@PostMapping("/signup")
	public ResponseEntity register(@Valid @RequestBody RegisterDTO data) throws IOException {
		
		if(this.userRepository.findByEmail(data.email()) != null) {
			return ResponseEntity.badRequest().body("email já está cadastrado");
		} 
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(
				data.name(),
				data.email(), 
				encryptedPassword, 
				data.phone(),
				data.address(),
				data.city(),
				data.stateId(),
				data.role()
				);
		
		this.userRepository.save(newUser);
		return login(new AuthenticationDTO(data.email(), data.password()));
	}
	
	@GetMapping("/userdata")
	public ResponseEntity getUserData(HttpServletRequest request, @RequestHeader("Authorization") String token ) throws IOException {
		var user = (User) request.getAttribute("user");
		List<Long> jobIdList = new ArrayList<Long>();
		
		if(user.getPathProfilePicture() != null) {	
			return ResponseEntity.ok(
					new LoginResponseDTO(token, new UserResponseDTO(
							user.getName(),
							user.getEmail(), 
							user.getId(),
							user.getPhone(),
							user.getAdress(),
							user.getCity(),
							user.getStateId(),
							userService.getImage(user.getId()).getContentAsByteArray()
							)));
		} else {
			return ResponseEntity.ok(
					new LoginResponseDTO(token, new UserResponseDTO(
							user.getName(),
							user.getEmail(), 
							user.getId(),
							user.getPhone(),
							user.getAdress(),
							user.getCity(),
							user.getStateId(),
							null
							)));
			}
	}
	
	@PostMapping("/profilepicture")
	public ResponseEntity saveProfilePicture(HttpServletRequest request, @RequestParam("file") MultipartFile file) {
		var user = (User) request.getAttribute("user");
		userService.saveImage(file, user.getId());
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/logout")
	public ResponseEntity logout(@RequestHeader("Authorization") String token) {
		tokenService.deactivateToken(token);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/cv")
	public ResponseEntity saveCV(HttpServletRequest request, @RequestParam("PDF") MultipartFile file) {
		var user = (User) request.getAttribute("user");
		userService.saveCV(file, user.getId());
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/cv")
	public ResponseEntity getCV(HttpServletRequest request) {
		var user = (User) request.getAttribute("user");
		return ResponseEntity.ok().body(userService.getCV(user.getId()));
	}
	
}
