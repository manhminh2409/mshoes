package com.mshoes.mshoes.models.DTO;

import lombok.Data;

@Data
public class RequestedUser {
    private String name;

    private String password;

    private String fullname;

    private String email;

    private String address;

    private String phone;

    private String image;
}
