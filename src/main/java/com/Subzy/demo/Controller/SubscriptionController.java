package com.Subzy.demo.Controller;

import com.Subzy.demo.Dtos.SubscribeRequest;
import com.Subzy.demo.Dtos.SubscriptionResponse;
import com.Subzy.demo.Services.SubscriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subscriptions")
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    // Create subscription
    @PostMapping("/create")
    public ResponseEntity<String> createSubscription(@RequestBody SubscribeRequest request) {
        return subscriptionService.createSubscription(request);
    }

    // Get all subscriptions
    @GetMapping
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions() {
        return subscriptionService.getAllSubscriptions();
    }

    // Get subscription by id
    @GetMapping("/{id}")
    public ResponseEntity<SubscriptionResponse> getSubscriptionById(@PathVariable Long id) {
        return subscriptionService.getSubscriptionById(id);
    }
}
