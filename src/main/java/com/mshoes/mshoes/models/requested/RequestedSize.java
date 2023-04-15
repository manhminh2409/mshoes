package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class RequestedSize {
    private String value;

    private int total;

    private long colorId;
}
