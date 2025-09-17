package com.Subzy.demo.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CompanyDashboardResponse {
    private long totalClients;
    private long activeSubscriptions;
    private long cancelledSubscriptions;
    private long pendingSubscriptions;
    private double totalRevenue;
    private double overdueRevenue;
}

