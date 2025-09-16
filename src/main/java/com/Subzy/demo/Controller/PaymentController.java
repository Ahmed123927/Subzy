package com.Subzy.demo.Controller;

import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Repository.InvoiceRepository;
import com.Subzy.demo.Services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;
    private final InvoiceRepository invoiceRepository;

    public PaymentController(PaymentService paymentService, InvoiceRepository invoiceRepository) {
        this.paymentService = paymentService;
        this.invoiceRepository = invoiceRepository;
    }

    @PostMapping("/checkout")
    public ResponseEntity<Map<String, String>> checkout(@RequestParam Long invoiceId) throws Exception {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));
        String url = paymentService.createCheckoutSession(invoiceId, invoice.getAmount(), "usd");
        return ResponseEntity.ok(Map.of("checkoutUrl", url));
    }
}
