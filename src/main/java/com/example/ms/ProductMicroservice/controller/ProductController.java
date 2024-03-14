package com.example.ms.ProductMicroservice.controller;

import com.example.ms.ProductMicroservice.models.CreateRestProductModel;
import com.example.ms.ProductMicroservice.models.ErrorMessage;
import com.example.ms.ProductMicroservice.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public ProductController(ProductService productService){
        this.productService = productService;
    }
    @PostMapping
    public ResponseEntity<Object> createProducts(@RequestBody CreateRestProductModel product) {
        String productId = null;
        try {
            productId = productService.createProduct(product);
        } catch (Exception e) {
            LOGGER.info("******* Exception in product controller *******");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(new Date(),e.getMessage(),"/products"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(productId);
    }
}
