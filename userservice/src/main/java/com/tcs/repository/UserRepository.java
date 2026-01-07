package com.tcs.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tcs.entity.Users;

public interface UserRepository extends JpaRepository<Users, Long> {

	Users findByUsernameAndIsDeletedFalse(String username);
	 
}