package com.mshoes.mshoes.repositories;

import com.mshoes.mshoes.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByEmail(String email);

	Optional<User> findByNameOrEmail(String name, String email);

	Optional<User> findByName(String name);

	Boolean existsByName(String name);

	Boolean existsByEmail(String email);
}
