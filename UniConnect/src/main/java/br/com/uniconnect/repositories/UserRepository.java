package br.com.uniconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import br.com.uniconnect.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	public UserDetails findByEmail(String email);
}
