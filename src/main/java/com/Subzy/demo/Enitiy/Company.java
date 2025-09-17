package com.Subzy.demo.Enitiy;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String industry;
    private String country;
    private String currency;
    private float vatRate;

    @OneToMany(mappedBy = "company",cascade = CascadeType.ALL)
    private List<Plan> plans=new ArrayList<>();


    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToMany(mappedBy = "companies")
    private List<Client> clients = new ArrayList<>();
}
