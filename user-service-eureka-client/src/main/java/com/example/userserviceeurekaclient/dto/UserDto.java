package com.example.userserviceeurekaclient.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.userserviceeurekaclient.vo.response.ResponseOrder;

import lombok.Data;

@Data
public class UserDto {
	private String email;
	private String name;
	private String password;
	private String userId;
	private LocalDateTime createdAt;

	private String encryptedPassword;
	private List<ResponseOrder> orders;
}
