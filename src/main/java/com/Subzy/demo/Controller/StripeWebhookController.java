package com.Subzy.demo.Controller;

import com.Subzy.demo.Services.PaymentService;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.ApiResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/webhook")
public class StripeWebhookController {

    private final PaymentService paymentService;

    public StripeWebhookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    public ResponseEntity<String> handleStripeEvent(
            @RequestBody String payload,
            @RequestHeader(value = "Stripe-Signature", required = false) String sigHeader) {
        try {
            Event event = ApiResource.GSON.fromJson(payload, Event.class);

            switch (event.getType()) {
                case "checkout.session.completed":
                    Session session = (Session) event.getDataObjectDeserializer()
                            .getObject().orElse(null);

                    if (session != null) {
                        String invoiceId = session.getMetadata().get("invoiceId");
                        String paymentIntentId = session.getPaymentIntent();

                        System.out.println("✅ Checkout completed for invoice " + invoiceId);

                        if (paymentIntentId != null) {
                            PaymentIntent intent = PaymentIntent.retrieve(paymentIntentId);
                            paymentService.handlePaymentSucceeded(intent, Long.valueOf(invoiceId));
                        }
                    }
                    break;

                case "payment_intent.payment_failed":
                    PaymentIntent failedIntent = (PaymentIntent) event.getDataObjectDeserializer()
                            .getObject().orElse(null);
                    if (failedIntent != null) {
                        paymentService.handlePaymentFailed(failedIntent);
                    }
                    break;

                default:
                    System.out.println("ℹ️ Event ignored: " + event.getType());
            }

            return ResponseEntity.ok("✅ Event received");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("⚠️ Error: " + e.getMessage());
        }
    }

}
