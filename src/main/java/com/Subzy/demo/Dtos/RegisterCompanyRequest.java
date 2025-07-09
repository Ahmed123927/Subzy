package com.Subzy.demo.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterCompanyRequest {
    private String fullName;
    private String email;
    private String password;

    private String companyName;
    private String industry;
    private String country;
    private String currency;
    private float vatRate;
}
