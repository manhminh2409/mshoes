package com.mshoes.mshoes.models.dtos;

import lombok.Data;

@Data
public class RequestedUser {
	private String username;

	private String password;

	private String fullname;

	private String email;

	private String address;

	private String phone;

	private String image;
}
