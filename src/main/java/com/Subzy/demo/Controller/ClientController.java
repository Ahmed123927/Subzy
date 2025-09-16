package com.Subzy.demo.Controller;

import com.Subzy.demo.Dtos.RegisterClientRequest;
import com.Subzy.demo.Dtos.RegisterCompanyRequest;
import com.Subzy.demo.Services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ResponseEntity<String> registerClient(@RequestBody RegisterClientRequest request) {
        return clientService.registerClient(request);
    }
}
