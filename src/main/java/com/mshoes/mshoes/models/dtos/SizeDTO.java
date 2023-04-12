package com.mshoes.mshoes.models.dtos;

import lombok.Data;

@Data
public class SizeDTO {
    private String value;

    private String sku;

    private int price;

    private int discountPrice;
}
