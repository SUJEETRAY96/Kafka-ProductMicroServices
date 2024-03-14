package com.example.ms.ProductMicroservice.services;

import com.example.ms.ProductMicroservice.models.CreateRestProductModel;

import java.util.concurrent.ExecutionException;

public interface ProductService {
    String createProduct(CreateRestProductModel product) throws Exception;
}
