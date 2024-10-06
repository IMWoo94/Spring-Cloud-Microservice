package com.example.catalogserviceeurekaclient.controller;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.catalogserviceeurekaclient.entity.CatalogEntity;
import com.example.catalogserviceeurekaclient.service.CatalogService;
import com.example.catalogserviceeurekaclient.vo.response.ResponseCatalog;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class CatalogController {

	private final CatalogService catalogService;
	private final Environment env;

	@GetMapping("/health_check")
	public String status() {
		return String.format("It's Working in User Service on PORT %s",
			env.getProperty("local.server.port"));
	}

	@GetMapping("/catalogs")
	public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
		Iterable<CatalogEntity> userList = catalogService.getAllCatalogs();
		List<ResponseCatalog> result = new ArrayList<>();

		userList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponseCatalog.class));
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

}
