package com.Subzy.demo.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PayInvoiceRequest {
    private Long invoiceId;
    private double amount;
    private String method;
}
