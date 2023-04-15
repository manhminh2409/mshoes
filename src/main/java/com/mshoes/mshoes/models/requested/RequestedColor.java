package com.mshoes.mshoes.models.requested;

import lombok.Data;

import java.util.List;

@Data
public class RequestedColor {
    private String value;

    private List<RequestedSize> sizes;

    private long productId;
}
