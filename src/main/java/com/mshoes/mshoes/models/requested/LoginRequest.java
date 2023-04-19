package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;

    private String password;
}
