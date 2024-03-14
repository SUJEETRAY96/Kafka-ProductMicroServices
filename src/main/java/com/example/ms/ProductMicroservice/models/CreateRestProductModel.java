package com.example.ms.ProductMicroservice.models;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
public class CreateRestProductModel {
    private String title;
    private BigDecimal price;
    private Integer quantity;


}
