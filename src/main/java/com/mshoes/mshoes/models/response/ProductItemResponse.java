package com.mshoes.mshoes.models.response;

import lombok.Data;

@Data
public class ProductItemResponse {
    private String name;

    private String description;

    private int visited;

    private String sku;

    private int price;

    private int discountPrice;
}
