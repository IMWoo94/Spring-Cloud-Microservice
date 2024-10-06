package com.example.userserviceeurekaclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.entity.UserEntity;
import com.example.userserviceeurekaclient.service.UserService;
import com.example.userserviceeurekaclient.vo.Greeting;
import com.example.userserviceeurekaclient.vo.request.RequestUser;
import com.example.userserviceeurekaclient.vo.response.ResponseUser;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
@Slf4j
public class UserController {

	private final Environment env;
	private final Greeting greeting;
	private final UserService userService;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s",
			env.getProperty("local.server.port"));
	}

	@GetMapping("/welcome")
	public String welcome() {
		// return env.getProperty("greeting.message");
		return greeting.getMessage();
	}

	@PostMapping("/users")
	public ResponseEntity<ResponseUser> createUser(
		@RequestBody RequestUser user
	) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(user, UserDto.class);
		userService.createUser(userDto);

		ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
	}

	@GetMapping("/users")
	public ResponseEntity<List<ResponseUser>> getUsers() {
		Iterable<UserEntity> userList = userService.getUserByAll();
		List<ResponseUser> result = new ArrayList<>();

		userList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseUser.class));
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/users/{userid}")
	public ResponseEntity<ResponseUser> getUser(
		@PathVariable String userid
	) {
		UserDto userDto = userService.getUserByUserId(userid);
		ResponseUser result = new ModelMapper().map(userDto, ResponseUser.class);
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
