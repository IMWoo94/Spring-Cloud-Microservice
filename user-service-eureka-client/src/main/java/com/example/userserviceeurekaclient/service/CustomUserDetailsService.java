package com.example.userserviceeurekaclient.service;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.entity.UserEntity;
import com.example.userserviceeurekaclient.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("CustomUserDetailsService username : {}", username);

		UserEntity userEntity = userRepository.findByEmail(username);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}

		return new User(userEntity.getEmail(),
			userEntity.getEncryptedPassword(),
			true,
			true,
			true,
			true,
			new ArrayList<>()
		);
	}

	public UserDto getCurrentUser(String username) {
		UserEntity userEntity = userRepository.findByEmail(username);
		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userEntity, UserDto.class);
		return userDto;
	}
}
