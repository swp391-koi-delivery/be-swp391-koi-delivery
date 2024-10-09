package com.SWP391.KoiXpress.Api;

import com.SWP391.KoiXpress.Entity.Policy;
import com.SWP391.KoiXpress.Service.PolicyService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Policy")
@CrossOrigin("*")
@SecurityRequirement(name="api")
@PreAuthorize("hasAuthority('CUSTOMER') or hasAuthority('Manager')")
public class PolicyAPI {
    @Autowired
    PolicyService policyService;

    @PostMapping
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity createPolicy(@Valid @RequestBody Policy policy) {
        Policy newPolicy = policyService.createPolicy(policy);
        return ResponseEntity.ok(newPolicy);
    }

    @GetMapping
    public ResponseEntity getPolicy() {
        List<Policy> policies = policyService.getPolicies();
        return ResponseEntity.ok(policies);
    }

    @PutMapping("{policyId}")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity updatrPolicy(@PathVariable long policyId, @Valid @RequestBody Policy policy) {
        Policy updatedPolicy = policyService.updatePolicy(policyId, policy);
        return ResponseEntity.ok(updatedPolicy);
    }

    @DeleteMapping("{policyId}")
    @PreAuthorize("hasAuthority('Manager')")
    public ResponseEntity deletePolicy(@PathVariable long policyId) {
        policyService.deletePolicy(policyId);
        return ResponseEntity.ok().build();
    }
}
