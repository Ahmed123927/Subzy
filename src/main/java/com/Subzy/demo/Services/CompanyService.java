package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.RegisterCompanyRequest;
import com.Subzy.demo.Enitiy.Company;
import com.Subzy.demo.Enitiy.Users;
import com.Subzy.demo.Repository.CompanyRepository;
import com.Subzy.demo.Repository.UserRepository;
import com.Subzy.demo.Utils.Roles;
import com.Subzy.demo.Utils.Status;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> RegisterCompany(RegisterCompanyRequest companyRequest){

        if(userRepository.existsByEmail(companyRequest.getEmail())){
            return ResponseEntity.badRequest().body("Email already exists");
        }

        Users user=Users.builder()
                .fullName(companyRequest.getFullName())
                .email(companyRequest.getEmail())
                .password(companyRequest.getPassword())
                .role(Roles.COMPANY_OWNER)
                .status(Status.ACTIVE)
                .createdAt(new Date())
                .build();

        Company company=Company.builder()
                .name(companyRequest.getCompanyName())
                .industry(companyRequest.getIndustry())
                .country(companyRequest.getCountry())
                .currency(companyRequest.getCurrency())
                .vatRate(companyRequest.getVatRate())
                .user(user)
                .build();

        userRepository.save(user);
        companyRepository.save(company);
        return ResponseEntity.ok("Company Registered Successfully!");
    }

}
