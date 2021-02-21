package com.ubs.teste.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.ubs.teste.api.dto.Datum;
import com.ubs.teste.api.dto.Product;
import com.ubs.teste.api.dto.ProductDTO;
import com.ubs.teste.api.model.Prod;
import com.ubs.teste.api.repository.ProductRepository;

@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	private List<Prod> listProd = new ArrayList<>();

	private int sotory = 0;

	private Prod prod = null;

	@Transactional
	public void productCalculation(ProductDTO productDTO) {

		sotory = productDTO.getStoryQuantity();

		List<Prod> list = new ArrayList<Prod>();
		 list = productRepository.findByProductEquals(productDTO.getProduct());

		int media = (list.size() ) / sotory;

		int resto = (list.size()) - (productDTO.getStoryQuantity() * media) ;
		
		try {
			
			int divisaoResto = productDTO.getStoryQuantity() / resto;
			
		} catch (Exception e) {
			System.out.println("não é possivel divisão por zero!!");
			
		}
		
		int modResto = productDTO.getStoryQuantity() - resto;
		
		int empresaComMaisProdutos = productDTO.getStoryQuantity() - modResto ;
		
		System.out.println("** Existem "+ productDTO.getStoryQuantity() + " empresas, " + list.size() +" produtos. "  + empresaComMaisProdutos +" empresas ficaram com "+(media + 1) +" produtos e " + modResto+" empresa ficaram com  "+media +" produto.");
		

	}

	@Transactional
	public List<Prod> findAll() {

		List<Prod> prods = new ArrayList<>();

		prods = productRepository.findAll();

		return prods;
	}

	public void read() {

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

							this.convertToPrord(datum, prod);
						}

					});

					try {
						this.productRepository.saveAll(listProd);
						
					} catch (Exception e) {
						System.out.println("Error na gravação do arquivo favor iniciar a aplicação novamente");
					}
				

				}
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void convertToPrord(Datum datum, Prod prod) {

		prod.setIndustry(datum.getIndustry());
		prod.setOrigin(datum.getOrigin());
		prod.setPrice(datum.getPrice());
		prod.setProduct(datum.getProduct());
		prod.setQuantity(datum.getQuantity());
		prod.setType(datum.getType());

		listProd.add(prod);

	}
}
