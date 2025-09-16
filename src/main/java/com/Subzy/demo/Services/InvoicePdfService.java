package com.Subzy.demo.Services;

import com.Subzy.demo.Enitiy.Invoice;
import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Plan;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
public class InvoicePdfService {

    public byte[] generateInvoicePdf(Invoice invoice) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);

        document.open();

        // عنوان
        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
        document.add(new Paragraph("Invoice #" + invoice.getId(), titleFont));
        document.add(new Paragraph(" "));

        // بيانات العميل
        Client client = invoice.getSubscription().getClient();
        document.add(new Paragraph("Client: " + client.getUser().getFullName()));
        document.add(new Paragraph("Email: " + client.getUser().getEmail()));
        document.add(new Paragraph("Phone: " + client.getPhone()));
        document.add(new Paragraph(" "));

        // تفاصيل الخطة
        Plan plan = invoice.getSubscription().getPlan();
        document.add(new Paragraph("Plan: " + plan.getName()));
        document.add(new Paragraph("Company: " + plan.getCompany().getName()));
        document.add(new Paragraph(" "));

        // جدول تفاصيل الفاتورة
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.addCell("Issue Date");
        table.addCell(invoice.getIssueDate().toString());

        table.addCell("Payment Date");
        table.addCell(invoice.getPaymentDate().toString());

        table.addCell("Amount");
        table.addCell(invoice.getAmount().toString() + " USD");

        table.addCell("Status");
        table.addCell(invoice.getStatus().name());

        document.add(table);

        document.close();
        return out.toByteArray();
    }
}
