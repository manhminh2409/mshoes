package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;

    private String password;

    private String fullname;

    private String email;
}
