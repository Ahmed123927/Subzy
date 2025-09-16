package com.Subzy.demo.Dtos;

import com.Subzy.demo.Utils.BillingCycle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PlanResponse {
    private Long id;
    private String name;
    private double price;
    private BillingCycle billingCycle;
    private int trialDays;
    private int maxUserAllowed;
    private boolean isActive;
    private String companyName;
}
