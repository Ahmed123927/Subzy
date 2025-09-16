package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.DtoMapper;
import com.Subzy.demo.Dtos.RegisterClientRequest;
import com.Subzy.demo.Enitiy.Client;
import com.Subzy.demo.Enitiy.Company;
import com.Subzy.demo.Enitiy.Users;
import com.Subzy.demo.Repository.ClientRepository;
import com.Subzy.demo.Repository.CompanyRepository;
import com.Subzy.demo.Repository.UserRepository;
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

        Optional<Company> companyOpt = companyRepository.findById(request.getCompanyId());
        if (companyOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Company not found");
        }
        Company company = companyOpt.get();


        Users user = DtoMapper.mapToUserFromClientRequest(request, request.getPassword());
        userRepository.save(user);

        Client client = DtoMapper.mapToClient(request, company, user);
        clientRepository.save(client);

        return ResponseEntity.ok("Client registered successfully");
    }
}
