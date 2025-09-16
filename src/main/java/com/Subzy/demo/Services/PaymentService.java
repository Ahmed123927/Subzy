package com.Subzy.demo.Services;

import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Enitiy.Payment;
import com.Subzy.demo.Enitiy.Subscription;
import com.Subzy.demo.Repository.InvoiceRepository;
import com.Subzy.demo.Repository.PaymentRepository;
import com.Subzy.demo.Repository.SubscriptionRepository;
import com.Subzy.demo.Utils.InvoiceStatus;
import com.Subzy.demo.Utils.PaymentMethod;
import com.Subzy.demo.Utils.PaymentStatus;
import com.Subzy.demo.Utils.SubscriptionStatus;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDate;
import com.stripe.Stripe;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final InvoiceRepository invoiceRepository;
    private final SubscriptionRepository subscriptionRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          InvoiceRepository invoiceRepository,
                          SubscriptionRepository subscriptionRepository) {
        this.paymentRepository = paymentRepository;
        this.invoiceRepository = invoiceRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;


    public String createCheckoutSession(Long invoiceId, double amount, String currency) throws Exception {

        Stripe.apiKey = stripeSecretKey;


        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:8080/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency(currency)
                                                .setUnitAmount((long) (amount * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Invoice #" + invoiceId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("invoiceId", invoiceId.toString())
                .build();
        Session session = Session.create(params);

        return session.getUrl();
    }
    @Transactional
    public void handlePaymentSucceeded(PaymentIntent intent, Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceId));

        double amount = intent.getAmount() != null ? intent.getAmount() / 100.0 : invoice.getAmount();
        Payment payment = Payment.builder()
                .invoice(invoice)
                .amount(amount)
                .method(PaymentMethod.STRIPE)
                .status(PaymentStatus.SUCCESS)
                .timestamp(new Timestamp(System.currentTimeMillis()))
                .build();
        paymentRepository.save(payment);

        invoice.setStatus(InvoiceStatus.PAID);
        invoice.setPaymentDate(LocalDate.now());
        invoiceRepository.save(invoice);

        Subscription sub = invoice.getSubscription();
        sub.setStatus(SubscriptionStatus.ACTIVE);
        subscriptionRepository.save(sub);

        System.out.println("✅ Invoice " + invoiceId + " marked as PAID and subscription activated.");
    }


    @Transactional
    public void handlePaymentFailed(PaymentIntent intent) {
        try {
            String invoiceIdStr = intent.getMetadata() != null ? intent.getMetadata().get("invoiceId") : null;
            if (invoiceIdStr == null) {
                System.out.println("⚠️ No invoiceId found in metadata for failed PaymentIntent " + intent.getId());
                return;
            }

            Long invoiceId = Long.valueOf(invoiceIdStr);

            Invoice invoice = invoiceRepository.findById(invoiceId)
                    .orElseThrow(() -> new RuntimeException("Invoice not found: " + invoiceId));

            double amount = intent.getAmount() != null ? intent.getAmount() / 100.0 : invoice.getAmount();
            Payment payment = Payment.builder()
                    .invoice(invoice)
                    .amount(amount)
                    .method(PaymentMethod.STRIPE)
                    .status(PaymentStatus.FAILED)
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            paymentRepository.save(payment);


            invoice.setStatus(InvoiceStatus.OVERDUE);
            invoiceRepository.save(invoice);


            Subscription subscription = invoice.getSubscription();
            subscription.setStatus(SubscriptionStatus.CANCELLED);
            subscriptionRepository.save(subscription);

            System.out.println("❌ Payment failed! Invoice " + invoiceId + " marked as OVERDUE");
        } catch (Exception e) {
            System.err.println("❌ Error in handlePaymentFailed: " + e.getMessage());
        }
    }
}
