package com.example.catalogserviceeurekaclient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.catalogserviceeurekaclient.entity.CatalogEntity;

public interface CatalogRepository extends JpaRepository<CatalogEntity, Long> {
	CatalogEntity findByProductId(String productId);
}
