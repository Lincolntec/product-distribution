package com.ubs.teste.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubs.teste.api.dto.DistribuitionDTO;
import com.ubs.teste.api.dto.ProductDTO;
import com.ubs.teste.api.model.Prod;
import com.ubs.teste.api.service.ProductService;


@RestController
@RequestMapping("/calculation")
public class CalculationController {
	
	@Autowired
	private ProductService productService;
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.OK)
	public DistribuitionDTO calculation(@RequestBody ProductDTO productDTO) {
		
		DistribuitionDTO distribuitionDTO = new DistribuitionDTO();

	//	distribuitionDTO = productService.productCalculation(productDTO);
		
		distribuitionDTO = productService.distributionAverageProductValue(productDTO);
		
		return distribuitionDTO;
		
	}

}
