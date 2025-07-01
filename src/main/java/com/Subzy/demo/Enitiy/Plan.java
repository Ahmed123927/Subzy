package com.Subzy.demo.Enitiy;

import com.Subzy.demo.Utils.BillingCycle;
import jakarta.persistence.*;
@Entity
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private double price;

    @Enumerated(EnumType.STRING)
    private BillingCycle billingCycle;
    private int trialDays;
    private int maxUserAllowed;
    private boolean isActive;

}
