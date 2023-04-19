package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class SizeRequest {
    private String value;

    private int total;

    private Long colorId;
}
