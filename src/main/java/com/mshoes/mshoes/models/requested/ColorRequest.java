package com.mshoes.mshoes.models.requested;

import lombok.Data;

import java.util.List;

@Data
public class ColorRequest {
    private String value;

    private List<SizeRequest> sizes;

    private Long productId;
}
