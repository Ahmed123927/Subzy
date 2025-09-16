package com.Subzy.demo.Controller;

import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Repository.InvoiceRepository;
import com.Subzy.demo.Services.InvoicePdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceRepository invoiceRepository;
    private final InvoicePdfService invoicePdfService;

    public InvoiceController(InvoiceRepository invoiceRepository, InvoicePdfService invoicePdfService) {
        this.invoiceRepository = invoiceRepository;
        this.invoicePdfService = invoicePdfService;
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<byte[]> downloadInvoice(@PathVariable Long id) throws Exception {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found"));

        byte[] pdfBytes = invoicePdfService.generateInvoicePdf(invoice);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice-" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
