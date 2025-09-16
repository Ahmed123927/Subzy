package com.Subzy.demo.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDto {
    private Long id;
    private String name;
    private String industry;
    private String country;
    private String currency;
    private float vatRate;
}
