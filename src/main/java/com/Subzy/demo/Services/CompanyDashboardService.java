package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.CompanyDashboardResponse;
import com.Subzy.demo.Dtos.DtoMapper;
import com.Subzy.demo.Dtos.UserDto;
import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Repository.ClientRepository;
import com.Subzy.demo.Repository.CompanyRepository;
import com.Subzy.demo.Repository.InvoiceRepository;
import com.Subzy.demo.Repository.SubscriptionRepository;
import com.Subzy.demo.Utils.InvoiceStatus;
import com.Subzy.demo.Utils.SubscriptionStatus;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyDashboardService {

    private final CompanyRepository companyRepository;
    private final ClientRepository clientRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final InvoiceRepository invoiceRepository;

    public CompanyDashboardResponse getDashboard(Long companyId) {
        long totalClients = clientRepository.countByCompanyId(companyId);
        long activeSubs = subscriptionRepository.countByPlanCompanyIdAndStatus(companyId, SubscriptionStatus.ACTIVE);
        long cancelledSubs = subscriptionRepository.countByPlanCompanyIdAndStatus(companyId, SubscriptionStatus.CANCELLED);
        long pendingSubs = subscriptionRepository.countByPlanCompanyIdAndStatus(companyId, SubscriptionStatus.PENDING);

        double totalRevenue = invoiceRepository.sumByCompanyAndStatus(companyId, InvoiceStatus.PAID);
        double overdueRevenue = invoiceRepository.sumByCompanyAndStatus(companyId, InvoiceStatus.OVERDUE);

        return CompanyDashboardResponse.builder()
                .totalClients(totalClients)
                .activeSubscriptions(activeSubs)
                .cancelledSubscriptions(cancelledSubs)
                .pendingSubscriptions(pendingSubs)
                .totalRevenue(totalRevenue)
                .overdueRevenue(overdueRevenue)
                .build();
    }




}
