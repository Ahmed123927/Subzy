package com.Subzy.demo.Controller;

import com.Subzy.demo.Dtos.CreatePlanRequest;
import com.Subzy.demo.Dtos.PlanResponse;
import com.Subzy.demo.Dtos.UpdatePlanRequest;
import com.Subzy.demo.Enitiy.Plan;
import com.Subzy.demo.Services.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/plans")
public class PlanController {

    @Autowired
    private PlanService planService;

    @PostMapping
    public ResponseEntity<String> createPlan(@RequestBody CreatePlanRequest request) {
        return planService.createPlan(request);
    }

    @GetMapping
    public ResponseEntity<List<PlanResponse>> getPlans() {
        return planService.getPlans();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Plan>> getPlan(@PathVariable Long id) {
        return (ResponseEntity<Optional<Plan>>) planService.getPlan(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePlan(@PathVariable Long id, @RequestBody UpdatePlanRequest request) {
        return planService.updatePlan(id, request);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok("Plan deleted successfully");
    }
}
