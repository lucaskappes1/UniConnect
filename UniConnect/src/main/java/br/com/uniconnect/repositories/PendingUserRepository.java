package br.com.uniconnect.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.uniconnect.entities.PendingUser;

public interface PendingUserRepository extends JpaRepository <PendingUser, Long>{

}
