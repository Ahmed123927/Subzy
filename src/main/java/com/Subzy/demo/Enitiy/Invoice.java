package com.Subzy.demo.Enitiy;

import com.Subzy.demo.Utils.InvoiceStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    private LocalDate issueDate;

    private LocalDate dueDate;

    @Column(nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status; // PENDING, PAID, OVERDUE, CANCELLED

    private LocalDate paymentDate;
}
