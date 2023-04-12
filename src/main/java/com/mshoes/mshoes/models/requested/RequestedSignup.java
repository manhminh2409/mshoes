package com.mshoes.mshoes.models.requested;

import lombok.Data;

@Data
public class RequestedSignup {
    private String username;

    private String password;

    private String fullname;

    private String email;
}
