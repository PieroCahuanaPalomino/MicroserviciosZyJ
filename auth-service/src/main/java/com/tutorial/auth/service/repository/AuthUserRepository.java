package com.tutorial.auth.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tutorial.auth.service.entity.AuthUser;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, Integer>{
	Optional<AuthUser> findByUserName(String username); //con mayusculas el find
}
