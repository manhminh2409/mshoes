package com.mshoes.mshoes.models.dtos;

import lombok.Data;

@Data
public class RequestedLogin {
    private String username;

    private String password;
}
