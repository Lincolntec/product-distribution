package com.ubs.teste.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ubs.teste.api.dto.ProductDTO;

import junit.framework.Assert;

public class CalculationControllerTest {

	@Autowired
	MockMvc mvc;

	@Test
	public void calculationController() {

		MvcResult mvcResult;

		try {
			mvcResult = mvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/calculation"))
					.andDo(System.err::print).andReturn();

			Assert.assertEquals(HttpStatus.OK, mvcResult.getResponse());

		} catch (Exception e) {

		}
	}

	@Test
	public void calculation() {

		ProductDTO prod = new ProductDTO();
		prod.setProduct("EMMS");
		prod.setStoryQuantity(5);

		int qtdProduct = 11;

		int media = qtdProduct / prod.getStoryQuantity();

		System.out.println("Media:" + media);

		// int resultado = prod.getStoryQuantity() % media;
		int resultado = qtdProduct % prod.getStoryQuantity();

		System.out.println("Resultado do mod: " + resultado);

		System.out.println("Existem " + prod.getStoryQuantity() + " empresas cada uma ficará com " + media
				+ " produtos e o restante ficará com " + resultado);

		int sobra = prod.getStoryQuantity() - resultado;

		if (media >= 2) {
			media = media + 1;
			System.out.println("Existem " + prod.getStoryQuantity() + " empresas " + (media + 1)
					+ " empresas ficaram com " + media + " produtos e o restante ficará com " + sobra);

			int teste = ((media + 1) * media);

			int teste2 = qtdProduct - teste;

			int teste3 = prod.getStoryQuantity() - resultado;

			System.out.println("Existem " + prod.getStoryQuantity() + " empresas " + (media + 1)
					+ " empresas ficaram com " + media + " produtos e o restante ficará com " + teste2);

			System.out.println(
					"Existem " + prod.getStoryQuantity() + " empresas " + (media + 1) + " empresas ficaram com " + media
							+ " produtos e " + teste3 + " empresa ficará com " + (media - 1));

		}

	}
}
