package com.example.userserviceeurekaclient.service;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.entity.UserEntity;

public interface UserService {
	UserDto createUser(UserDto userDto);

	UserDto getUserByUserId(String userId);

	Iterable<UserEntity> getUserByAll();

	UserDto getUserDetailsByEmail(String username);
}
