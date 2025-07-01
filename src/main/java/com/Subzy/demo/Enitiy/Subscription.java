package com.Subzy.demo.Enitiy;

import com.Subzy.demo.Utils.SubscriptionStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Plan plan;

    @Enumerated(EnumType.STRING)
    private SubscriptionStatus status;

    private LocalDate startDate;

    private LocalDate trialEndDate;

    private LocalDate endDate;

    private boolean autoRenew;
}
