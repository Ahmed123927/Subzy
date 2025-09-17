package com.Subzy.demo.Controller;

import com.Subzy.demo.Dtos.CompanyDashboardResponse;
import com.Subzy.demo.Services.CompanyDashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
@RequiredArgsConstructor
public class CompanyDashboardController {

    private final CompanyDashboardService dashboardService;

    @GetMapping("/{companyId}/dashboard")
    public ResponseEntity<CompanyDashboardResponse> getDashboard(@PathVariable Long companyId) {
        return ResponseEntity.ok(dashboardService.getDashboard(companyId));
    }
}

