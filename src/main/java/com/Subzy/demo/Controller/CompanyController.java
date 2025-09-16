package com.Subzy.demo.Controller;

import com.Subzy.demo.Dtos.RegisterCompanyRequest;
import com.Subzy.demo.Services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @PostMapping("/register")
    public ResponseEntity<String> registerCompany(@RequestBody RegisterCompanyRequest registerCompanyRequest) {
        return companyService.RegisterCompany(registerCompanyRequest);
    }
}
