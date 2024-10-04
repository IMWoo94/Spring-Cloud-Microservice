package com.example.userserviceeurekaclient.service;

import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.entity.UserEntity;
import com.example.userserviceeurekaclient.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;

	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(userDto, UserEntity.class);
		// TODO: password 암호화 처리 필요
		userEntity.setEncryptedPassword("encrypted_password");

		userRepository.save(userEntity);
		return null;
	}
}
