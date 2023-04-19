package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class OrderItemRequest {
    private Long sizeId;
    private int quantity;
}
