package com.Subzy.demo.Dtos;

import com.Subzy.demo.Dtos.*;
import com.Subzy.demo.Enitiy.*;
import com.Subzy.demo.Utils.*;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Date;


public class DtoMapper {



    public static Users mapToUserFromCompanyRequest(RegisterCompanyRequest request, String encodedPassword) {
        return Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Roles.COMPANY_OWNER)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .build();
    }

    public static Company mapToCompany(RegisterCompanyRequest request, Users user) {
        return Company.builder()
                .name(request.getCompanyName())
                .industry(request.getIndustry())
                .country(request.getCountry())
                .currency(request.getCurrency())
                .vatRate(request.getVatRate())
                .user(user)
                .build();
    }

    public static Users mapToUserFromClientRequest(RegisterClientRequest request, String encodedPassword) {
        return Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(encodedPassword)
                .role(Roles.CLIENT)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .build();
    }

    public static Client mapToClient(RegisterClientRequest request, Company company, Users user) {
        Client client = new Client();
        client.setPhone(request.getPhone());
        client.setCompany(company);
        client.setUser(user);
        return client;
    }

    public static Plan mapToPlan(CreatePlanRequest request, Company company) {
        return Plan.builder()
                .name(request.getName())
                .price(request.getPrice())
                .billingCycle(BillingCycle.valueOf(request.getBillingCycle()))
                .trialDays(request.getTrialDays())
                .maxUserAllowed(request.getMaxUserAllowed())
                .isActive(request.isActive())
                .company(company)
                .build();
    }

    public static Subscription mapToSubscription(SubscribeRequest request, Client client, Plan plan) {
        LocalDate startDate = LocalDate.now();
        LocalDate trialEndDate = startDate.plusDays(plan.getTrialDays());


        LocalDate endDate;
        switch (plan.getBillingCycle()) {
            case MONTHLY -> endDate = startDate.plusMonths(1);
            case YEARLY -> endDate = startDate.plusYears(1);
            default -> endDate = startDate.plusMonths(1); // fallback
        }

        return Subscription.builder()
                .client(client)
                .plan(plan)
                .status(SubscriptionStatus.ACTIVE)
                .startDate(startDate)
                .trialEndDate(trialEndDate)
                .endDate(endDate)
                .autoRenew(request.isAutoRenew())
                .build();
    }
    public static Payment mapToPayment(PayInvoiceRequest request, Invoice invoice) {
        return Payment.builder()
                .invoice(invoice)
                .amount(request.getAmount())
                .method(PaymentMethod.valueOf(request.getMethod().toUpperCase()))
                .status(PaymentStatus.SUCCESS)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
    }



    public static SubscriptionResponse mapToSubscriptionResponse(Subscription subscription) {
        return SubscriptionResponse.builder()
                .id(subscription.getId())
                .clientName(subscription.getClient().getUser().getFullName())
                .planName(subscription.getPlan().getName())
                .companyName(subscription.getPlan().getCompany().getName())
                .status(subscription.getStatus().name())
                .startDate(subscription.getStartDate())
                .trialEndDate(subscription.getTrialEndDate())
                .endDate(subscription.getEndDate())
                .autoRenew(subscription.isAutoRenew())
                .build();
    }

    // ======================
    // Entity → Response DTO
    // ======================
    public static PlanResponse mapToPlanResponse(Plan plan) {
        return PlanResponse.builder()
                .id(plan.getId())
                .name(plan.getName())
                .price(plan.getPrice())
                .billingCycle(plan.getBillingCycle())
                .trialDays(plan.getTrialDays())
                .maxUserAllowed(plan.getMaxUserAllowed())
                .isActive(plan.isActive())
                .companyName(plan.getCompany().getName())
                .build();
    }


    public static CompanyDto mapToCompanyDto(Company company) {
        return CompanyDto.builder()
                .id(company.getId())
                .name(company.getName())
                .industry(company.getIndustry())
                .currency(company.getCurrency())
                .vatRate(company.getVatRate())
                .country(company.getCountry())
                .build();
    }

    public static UserDto mapToUserDto(Users user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .fullName(user.getFullName())
                .role(user.getRole().name())
                .build();
    }




}
