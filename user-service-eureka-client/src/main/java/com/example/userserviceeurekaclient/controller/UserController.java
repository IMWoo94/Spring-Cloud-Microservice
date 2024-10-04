package com.example.userserviceeurekaclient.controller;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.core.env.Environment;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userserviceeurekaclient.dto.UserDto;
import com.example.userserviceeurekaclient.service.UserService;
import com.example.userserviceeurekaclient.vo.Greeting;
import com.example.userserviceeurekaclient.vo.request.RequestUser;

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
		return "It's Working in User Service";
	}

	@GetMapping("/welcome")
	public String welcome() {
		// return env.getProperty("greeting.message");
		return greeting.getMessage();
	}

	@PostMapping("/users")
	public String createUser(
		@RequestBody RequestUser user
	) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		UserDto userDto = mapper.map(user, UserDto.class);

		userService.createUser(userDto);
		return "Create user method ";
	}
}
