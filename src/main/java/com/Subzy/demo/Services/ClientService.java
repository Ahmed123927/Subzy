package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.DtoMapper;
import com.Subzy.demo.Dtos.RegisterClientRequest;
import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Company;
import com.Subzy.demo.Enitiy.Users;
import com.Subzy.demo.Repository.ClientRepository;
import com.Subzy.demo.Repository.CompanyRepository;
import com.Subzy.demo.Repository.UserRepository;
import com.Subzy.demo.Utils.Roles;
import com.Subzy.demo.Utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CompanyRepository companyRepository;


    public ResponseEntity<String> registerClient(RegisterClientRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Users user = Users.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(request.getPassword())
                .role(Roles.CLIENT)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .build();
        userRepository.save(user);

        Client client = Client.builder()
                .phone(request.getPhone())
                .user(user)
                .build();
        clientRepository.save(client);

        return ResponseEntity.ok("Client registered successfully");
    }


}
