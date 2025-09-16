package com.Subzy.demo.Dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePlanRequest {
    private String name;
    private double price;
    private String billingCycle;
    private int trialDays;
    private int maxUserAllowed;
    private boolean isActive;
}
