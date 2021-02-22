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
import com.ubs.teste.api.dto.DistribuitionDTO;
import com.ubs.teste.api.dto.Product;
import com.ubs.teste.api.dto.ProductDTO;
import com.ubs.teste.api.model.Prod;
import com.ubs.teste.api.repository.ProductRepository;

@Service
public class ProductService {


	@Autowired
	private ProductRepository productRepository;

	private List<Prod> listProd = new ArrayList<>();
	List<Prod> products;

	private int sotory = 0;
	
	private Prod prod = null;

	@Transactional
	public DistribuitionDTO productCalculation(ProductDTO productDTO) {

		DistribuitionDTO distribuitionDTO = new DistribuitionDTO();
		
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
		
		implementsTheDistribuition(productDTO, distribuitionDTO, list, media, modResto, empresaComMaisProdutos);
		
		
		System.out.println("** Existem "+ productDTO.getStoryQuantity() + " empresas, " + list.size() +" produtos. "  + empresaComMaisProdutos +" empresas ficaram com "+(media + 1) +
					" produtos e " + modResto+" empresa ficaram com  "+media +" produto.");
		
		return distribuitionDTO;

	}

	private void implementsTheDistribuition(ProductDTO productDTO, DistribuitionDTO distribuitionDTO, List<Prod> list,
			int media, int modResto, int empresaComMaisProdutos) {
		distribuitionDTO.setStoryQuantity(productDTO.getStoryQuantity());
		distribuitionDTO.setTotalQuantityProducts(list.size());
		distribuitionDTO.setCompaneisWhitMoreProducts(empresaComMaisProdutos);
		distribuitionDTO.setBiggerProduct(media+1);
		distribuitionDTO.setCompaniesWithFewerProducts(modResto);
		distribuitionDTO.setSmallerProduct(media);
	}

	@Transactional
	public List<Prod> findAll() {

		List<Prod> prods = new ArrayList<>();

		prods = productRepository.findAll();

		return prods;
	}

	public void read() {

		System.out.println("Iniciando Leitura");
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
						System.out.println("Iniciando Gravação");
					} catch (Exception e) {
						System.out.println("Error na gravação do arquivo favor iniciar a aplicação novamente");
					}
				
					System.out.println("Finalizado Gravaçao");
				}
			}
		} catch (FileNotFoundException ex) {
			Logger.getLogger(Prod.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	private void convertToPrord(Datum datum, Prod prod) {

		prod.setIndustry(datum.getIndustry());
		prod.setOrigin(datum.getOrigin());
		
		prod.setPrice(Double.parseDouble(datum.getPrice().replace("$", "")));
		prod.setProduct(datum.getProduct());
		prod.setQuantity(datum.getQuantity());
		prod.setType(datum.getType());

		listProd.add(prod);

	}
	
	public DistribuitionDTO distributionAverageProductValue(ProductDTO productDTO) {
		
DistribuitionDTO distribuitionDTO = new DistribuitionDTO();
		
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
		
		implementsTheDistribuition(productDTO, distribuitionDTO, list, media, modResto, empresaComMaisProdutos);
		
		
		System.out.println("** Existem "+ productDTO.getStoryQuantity() + " empresas, " + list.size() +" produtos. "  + empresaComMaisProdutos +" empresas ficaram com "+(media + 1) +
					" produtos e " + modResto+" empresa ficaram com  "+media +" produto.");
		
		System.out.println("Valor medio da empresa 1: " + ((media + 1) * prod.getPrice()));
		
		System.out.println("O valor medio da empresa 2 é: " + (media) * prod.getPrice());
		
		System.out.println("Valor de cada produto: " + prod.getPrice());
		
		return distribuitionDTO;
	}
	
}
