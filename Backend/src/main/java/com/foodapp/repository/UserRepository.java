package com.foodapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.foodapp.entity.User;
import com.foodapp.enums.UserRole;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findFirstByEmail(String email);

	User findByRole(UserRole admin);

	boolean existsByEmail(String email);
}