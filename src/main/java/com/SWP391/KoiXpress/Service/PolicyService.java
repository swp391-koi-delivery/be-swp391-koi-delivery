package com.SWP391.KoiXpress.Service;

import com.SWP391.KoiXpress.Entity.Enum.PolicyStatus;
import com.SWP391.KoiXpress.Entity.Policy;
import com.SWP391.KoiXpress.Exception.EntityNotFoundException;
import com.SWP391.KoiXpress.Repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PolicyService {
    @Autowired
    PolicyRepository policyRepository;

    public Policy createPolicy(Policy policy) {
        policy = new Policy();
        policy.setTitle(policy.getTitle());
        policy.setPost(policy.getPost());
        policy.setPolicyStatus(PolicyStatus.Active);
        return policyRepository.save(policy);
    }

    public List<Policy> getPolicies() {
        List<Policy> policies = policyRepository.findAll();
        return policies;
    }

    public Policy getPolicyById(long id) {
        Policy oldPolicy = policyRepository.findById(id).get();
        if (oldPolicy == null) {
            throw new EntityNotFoundException("Policy with id " + id + " not found");
        }
        return oldPolicy;
    }

    public Policy updatePolicy(long id, Policy policy) {
        Policy oldPolicy = getPolicyById(id);
        try {
            oldPolicy.setTitle(policy.getTitle());
            oldPolicy.setPost(policy.getPost());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return policyRepository.save(policy);
    }

    public void deletePolicy(long id) {
        Policy oldPolicy = getPolicyById(id);
        oldPolicy.setPolicyStatus(PolicyStatus.Terminated);
        policyRepository.save(oldPolicy);
    }

}
