package com.example.catalogserviceeurekaclient.service;

import com.example.catalogserviceeurekaclient.entity.CatalogEntity;

public interface CatalogService {
	Iterable<CatalogEntity> getAllCatalogs();
}
