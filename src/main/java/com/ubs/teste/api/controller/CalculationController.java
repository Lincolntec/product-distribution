package com.ubs.teste.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ubs.teste.api.dto.DistribuitionDTO;
import com.ubs.teste.api.dto.ProductDTO;
import com.ubs.teste.api.service.ProductService;

@RestController
@RequestMapping("/calculation")
public class CalculationController {

    private static Logger logger = LoggerFactory.getLogger(CalculationController.class);

    @Autowired
    private ProductService productService;

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public DistribuitionDTO calculation(@RequestBody ProductDTO productDTO) {

        logger.info("Iniciando o método calculation da classe controller");

        return productService.distributionAverageProductValue(productDTO);

    }

}
