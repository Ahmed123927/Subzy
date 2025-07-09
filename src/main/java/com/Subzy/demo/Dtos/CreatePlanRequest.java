package com.Subzy.demo.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreatePlanRequest {
    private String name;
    private double price;
    private String billingCycle;
    private int trialDays;
    private int maxUserAllowed;
    private boolean isActive;
    private Long companyId;
}
