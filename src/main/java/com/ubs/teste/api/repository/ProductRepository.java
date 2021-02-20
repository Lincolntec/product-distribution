package com.ubs.teste.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ubs.teste.api.model.Prod;

public interface ProductRepository extends JpaRepository<Prod, Long> {
	
	public List<Prod> findByProductEquals(String produto);

}
