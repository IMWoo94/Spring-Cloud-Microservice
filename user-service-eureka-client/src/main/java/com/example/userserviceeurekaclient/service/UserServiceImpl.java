package com.example.userserviceeurekaclient.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.entity.UserEntity;
import com.example.userserviceeurekaclient.repository.UserRepository;
import com.example.userserviceeurekaclient.vo.response.ResponseOrder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		userDto.setUserId(UUID.randomUUID().toString());
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		UserEntity userEntity = mapper.map(userDto, UserEntity.class);

		// BCryptPasswordEncoder 를 통해 단방향으로 암호와 처리.
		// 이후 BCryptPasswordEncoder 를 복호화 할 수는 없지만 동일한 값인지 확인을 통해 검증이 가능하다.
		String pwdEncode = passwordEncoder.encode(userDto.getPassword());
		log.info("password encode check : {} | {}", passwordEncoder.matches(userDto.getPassword(), pwdEncode),
			pwdEncode);

		userEntity.setEncryptedPassword(pwdEncode);
		userRepository.save(userEntity);

		return mapper.map(userEntity, UserDto.class);
	}

	@Override
	public UserDto getUserByUserId(String userId) {
		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity == null) {
			throw new UsernameNotFoundException("User not found");
		}
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(userEntity, UserDto.class);

		// TODO : 주문 정보 조회
		List<ResponseOrder> orders = new ArrayList<>();
		userDto.setOrders(orders);

		return userDto;
	}

	@Override
	public Iterable<UserEntity> getUserByAll() {
		return userRepository.findAll();
	}

	@Override
	public UserDto getUserDetailsByEmail(String username) {
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
