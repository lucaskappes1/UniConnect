package br.com.uniconnect.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.com.uniconnect.entities.User;
import br.com.uniconnect.repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

	private String basePathCV = "src/main/resources/static/CV/";
	private String basePathProfilePicture = "src/main/resources/static/profilepicture/";
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		return userRepository.findByEmail(email);
	}

	public UrlResource getImage(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		String fullPath = basePathProfilePicture + user.get().getPathProfilePicture();

		Path filePath = Paths.get(fullPath);

		try {
			UrlResource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("could not read media");
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException("media not found");
		}

	}

	public void saveImage(MultipartFile file, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		String fullPath = basePathProfilePicture + user.get().getId() + "";
		Path userDirectory = Paths.get(fullPath);
		try {
			if(!Files.exists(userDirectory)) {
				Files.createDirectories(userDirectory);
			}
		
			String fileName = "profilepicture.jpg";
			Path filePath = userDirectory.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			user.get().setPathProfilePicture(user.get().getId() + "/" + fileName);
			userRepository.save(user.get());
			
			} catch(IOException e) {
				
			}
	}
	
	public UrlResource getCV(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		String fullPath = basePathCV + user.get().getPathCV();

		Path filePath = Paths.get(fullPath);

		try {
			UrlResource resource = new UrlResource(filePath.toUri());

			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("could not read media");
			}

		} catch (MalformedURLException e) {
			throw new RuntimeException("media not found");
		}

	}

	public void saveCV(MultipartFile file, Long userId) {
		Optional<User> user = userRepository.findById(userId);
		String fullPath = basePathCV + user.get().getId() + "";
		Path userDirectory = Paths.get(fullPath);
		try {
			if(!Files.exists(userDirectory)) {
				Files.createDirectories(userDirectory);
			}
		
			String fileName = "CV.pdf";
			Path filePath = userDirectory.resolve(fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
			user.get().setPathCV(user.get().getId() + "/" + fileName);
			userRepository.save(user.get());
			
			} catch(IOException e) {
				
			}
	}

}
