package com.example.catalogserviceeurekaclient.service;

import org.springframework.stereotype.Service;

import com.example.catalogserviceeurekaclient.entity.CatalogEntity;
import com.example.catalogserviceeurekaclient.repository.CatalogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogServiceImpl implements CatalogService {

	private final CatalogRepository catalogRepository;

	@Override
	public Iterable<CatalogEntity> getAllCatalogs() {
		return catalogRepository.findAll();
	}
}
