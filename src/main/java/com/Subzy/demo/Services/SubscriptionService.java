package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.DtoMapper;
import com.Subzy.demo.Dtos.SubscribeRequest;
import com.Subzy.demo.Dtos.SubscriptionResponse;
import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Enitiy.Plan;
import com.Subzy.demo.Enitiy.Subscription;
import com.Subzy.demo.Repository.ClientRepository;
import com.Subzy.demo.Repository.InvoiceRepository;
import com.Subzy.demo.Repository.PlanRepository;
import com.Subzy.demo.Repository.SubscriptionRepository;
import com.Subzy.demo.Utils.InvoiceStatus;
import com.Subzy.demo.Utils.SubscriptionStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private InvoiceRepository invoiceRepository;

    public ResponseEntity<String> createSubscription(SubscribeRequest request) {
        Optional<Plan> planOpt = planRepository.findById(request.getPlanId());
        if (planOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Plan not found");
        }

        Optional<Client> clientOpt = clientRepository.findById(request.getClientId());
        if (clientOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Client not found");
        }

        Plan plan = planOpt.get();
        Client client = clientOpt.get();

        Subscription subscription = DtoMapper.mapToSubscription(request, client, plan);
        subscription.setStatus(SubscriptionStatus.PENDING);

        Subscription savedSub = subscriptionRepository.save(subscription);

        Invoice invoice = Invoice.builder()
                .subscription(savedSub)
                .issueDate(LocalDate.now())
                .dueDate(LocalDate.now().plusDays(7))
                .amount(plan.getPrice())
                .status(InvoiceStatus.PENDING)
                .build();

        invoiceRepository.save(invoice);

        return ResponseEntity.ok("Subscription created, invoice generated with amount " + plan.getPrice());
    }

    // ✅ تعديل getAllSubscriptions
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        List<SubscriptionResponse> responseList = subscriptions.stream()
                .map(DtoMapper::mapToSubscriptionResponse)
                .toList();

        return ResponseEntity.ok(responseList);
    }

    // ✅ تعديل getSubscriptionById
    public ResponseEntity<SubscriptionResponse> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id)
                .map(DtoMapper::mapToSubscriptionResponse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
