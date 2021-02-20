package com.ubs.teste.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubs.teste.api.dto.ProductDTO;
import com.ubs.teste.api.model.Prod;
import com.ubs.teste.api.service.ProductService;

@RestController
@RequestMapping("/read")
public class ReadController {

	@Autowired
	private ProductService productService;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public void save(ProductDTO productDTO) {

		productService.read();
	}
	
	@GetMapping("/listar")
	@ResponseStatus(HttpStatus.OK)
	public List<Prod> listar(ProductDTO productDTO) {

		return productService.findAll();
	}

}
