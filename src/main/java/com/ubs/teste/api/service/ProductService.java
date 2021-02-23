package com.ubs.teste.api.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger logger = LoggerFactory.getLogger(ProductService.class);

    @Autowired
    private ProductRepository productRepository;

    private List<Prod> listProd;

    private Prod prod = null;

    @Transactional
    public List<Prod> findAll() {

        logger.info("Starting the findAll method");

        List<Prod> prods = productRepository.findAll();

        logger.info("Ending the findAll method");

        return prods;
    }

    public void read() {

        logger.info("Starting the read method");

        listProd = new ArrayList<>();

        try {
            Product product = null;

            File diretorio = new File("./arquivos");
            if (diretorio.isDirectory()) {
                for (File arquivo : diretorio.listFiles()) {
                    Gson gson = new Gson();
                    BufferedReader reader = new BufferedReader(new FileReader(arquivo));

                    product = gson.fromJson(reader, Product.class);

                    logger.info("Finishes reading data from files.");

                    product.getData().parallelStream().forEach(datum -> {
                        prod = new Prod();

                        if (datum != null) {

                            this.convertToProduct(datum, prod);
                        }

                    });

                    try {
                        logger.info("Saving files started.");
                        this.productRepository.saveAll(listProd);

                    } catch (Exception e) {
                        logger.info("Error writing files");
                    }

                    logger.info("Finished recording the files.");
                }
            }
        } catch (FileNotFoundException ex) {

            logger.error("Error: " + ex.toString());
        }

    }

    public DistribuitionDTO distributionAverageProductValue(ProductDTO productDTO) {

        logger.info("Starting the distributionAverageProductValue method");

        DistribuitionDTO distribuitionDTO = new DistribuitionDTO();

        int story = productDTO.getStoryQuantity();

        List<Prod> list = productRepository.findByProductEquals(productDTO.getProduct());

        int media = (list.size()) / story;

        int resto = (list.size()) - (productDTO.getStoryQuantity() * media);

        int modResto = productDTO.getStoryQuantity() - resto;

        int empresaComMaisProdutos = productDTO.getStoryQuantity() - modResto;

        implementsTheDistribuition(productDTO, distribuitionDTO, list, media, modResto, empresaComMaisProdutos);

        logger.info("** Existem " + productDTO.getStoryQuantity() + " empresas, " + list.size() + " produtos. "
                + empresaComMaisProdutos + " empresas ficaram com " + (media + 1) + " produtos e " + modResto
                + " empresa ficaram com  " + media + " produto.");

        logger.info("Valor medio da empresa 1: " + ((media + 1) * prod.getPrice()));

        logger.info("O valor medio da empresa 2 é: " + (media) * prod.getPrice());

        logger.info("Valor de cada produto: " + prod.getPrice());

        return distribuitionDTO;
    }

    private void convertToProduct(Datum datum, Prod prod) {

        logger.info("Starting the convertToProduct method");

        prod.setIndustry(datum.getIndustry());
        prod.setOrigin(datum.getOrigin());

        prod.setPrice(Double.parseDouble(datum.getPrice().replace("$", "")));
        prod.setProduct(datum.getProduct());
        prod.setQuantity(datum.getQuantity());
        prod.setType(datum.getType());

        listProd.add(prod);

    }

    private void implementsTheDistribuition(ProductDTO productDTO, DistribuitionDTO distribuitionDTO, List<Prod> list,
                                            int media, int modResto, int empresaComMaisProdutos) {

        logger.info("Starting the implementsTheDistribuition method");

        distribuitionDTO.setStoryQuantity(productDTO.getStoryQuantity());
        distribuitionDTO.setTotalQuantityProducts(list.size());
        distribuitionDTO.setCompaneisWhitMoreProducts(empresaComMaisProdutos);
        distribuitionDTO.setBiggerProduct(media + 1);
        distribuitionDTO.setCompaniesWithFewerProducts(modResto);
        distribuitionDTO.setSmallerProduct(media);
    }

}
