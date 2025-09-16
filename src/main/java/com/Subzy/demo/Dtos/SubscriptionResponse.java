package com.Subzy.demo.Dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@Builder
public class SubscriptionResponse {
    private Long id;
    private String clientName;
    private String planName;
    private String companyName;
    private String status;
    private LocalDate startDate;
    private LocalDate trialEndDate;
    private LocalDate endDate;
    private boolean autoRenew;
}
