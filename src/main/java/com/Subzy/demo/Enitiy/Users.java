package com.Subzy.demo.Enitiy;

import com.Subzy.demo.Utils.Roles;
import com.Subzy.demo.Utils.Status;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private String email;

    private String password;
    @Enumerated(EnumType.STRING)
    private Roles role;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @OneToOne(mappedBy = "user")
    private Company company;

    @OneToOne(mappedBy = "user")
    private Client client;

}
