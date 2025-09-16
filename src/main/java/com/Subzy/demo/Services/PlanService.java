package com.Subzy.demo.Services;

import com.Subzy.demo.Dtos.CreatePlanRequest;
import com.Subzy.demo.Dtos.DtoMapper;
import com.Subzy.demo.Dtos.PlanResponse;
import com.Subzy.demo.Dtos.UpdatePlanRequest;
import com.Subzy.demo.Enitiy.Company;
import com.Subzy.demo.Enitiy.Plan;
import com.Subzy.demo.Repository.CompanyRepository;
import com.Subzy.demo.Repository.PlanRepository;
import com.Subzy.demo.Utils.BillingCycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;

    @Autowired
    private CompanyRepository companyRepository;

    public ResponseEntity<String> createPlan(CreatePlanRequest request){

        Optional<Company> companyOpt= companyRepository.findById(request.getCompanyId());
        if (companyOpt.isEmpty()) return ResponseEntity.badRequest().body("Company not found");

        Company company=companyOpt.get();

        Plan plan= DtoMapper.mapToPlan(request,company);
        planRepository.save(plan);

        return ResponseEntity.ok("Plan Created successfully");
    }

    public ResponseEntity<List<PlanResponse>> getPlans(){
        List<PlanResponse> plans = planRepository.findAll()
                .stream()
                .map(DtoMapper::mapToPlanResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(plans);
    }

    public ResponseEntity<?> getPlan(Long id) {
        Optional<Plan> planOpt = planRepository.findById(id);
        if (planOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Plan not found");
        }

        PlanResponse response = DtoMapper.mapToPlanResponse(planOpt.get());
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<String> deletePlan(Long id) {
        if (!planRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Plan not found");
        }
        planRepository.deleteById(id);
        return ResponseEntity.ok("Plan deleted successfully");
    }

    public ResponseEntity<String> updatePlan(Long id, UpdatePlanRequest request) {
        Optional<Plan> planOpt = planRepository.findById(id);
        if (planOpt.isEmpty()) {
            return ResponseEntity.badRequest().body("Plan not found");
        }

        Plan plan = planOpt.get();
        plan.setName(request.getName());
        plan.setPrice(request.getPrice());
        plan.setBillingCycle(BillingCycle.valueOf(request.getBillingCycle()));
        plan.setTrialDays(request.getTrialDays());
        plan.setMaxUserAllowed(request.getMaxUserAllowed());
        plan.setActive(request.isActive());

        planRepository.save(plan);

        return ResponseEntity.ok("Plan updated successfully");
    }


}
