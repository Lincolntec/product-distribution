package com.ubs.teste.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.google.gson.Gson;
import com.ubs.teste.api.dto.Datum;
import com.ubs.teste.api.dto.Product;
import com.ubs.teste.api.model.Prod;

public class ProductServiceTest {

	private List<Prod> listProd = new ArrayList<>();

	private Prod prod;

	@Test
	public void read() {

		try {

			File diretorio = new File("./arquivos");
			if (diretorio.isDirectory()) {
				File arquivos[] = diretorio.listFiles();
				for (File arquivo : arquivos) {
					BufferedReader reader = new BufferedReader(new FileReader(arquivo));

					Assert.assertNotNull(reader);
				}
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	@Test
	public void readAndConvertToObj() {
		try {
			Product product = null;

			File diretorio = new File("./arquivos");
			if (diretorio.isDirectory()) {
				File arquivos[] = diretorio.listFiles();
				for (File arquivo : arquivos) {
					Gson gson = new Gson();
					BufferedReader reader = new BufferedReader(new FileReader(arquivo));

					product = gson.fromJson(reader, Product.class);

					product.getData().parallelStream().forEach(datum -> {
						prod = new Prod();
						if (datum != null) {

							List<Prod> list = this.convertToProd(datum, prod);

							Assert.assertNotNull(list);
						}

					});

				}
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private List<Prod> convertToProd(Datum datum, Prod prod) {

		prod.setIndustry(datum.getIndustry());
		prod.setOrigin(datum.getOrigin());
	//	prod.setPrice(datum.getPrice());
		prod.setProduct(datum.getProduct());
		prod.setQuantity(datum.getQuantity());
		prod.setType(datum.getType());

		listProd.add(prod);

		return listProd;

	}

}
