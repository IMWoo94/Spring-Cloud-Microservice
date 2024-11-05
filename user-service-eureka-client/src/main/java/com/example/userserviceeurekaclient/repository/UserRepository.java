package com.example.userserviceeurekaclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userserviceeurekaclient.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	UserEntity findByUserId(String userId);

	UserEntity findByEmail(String email);
}
