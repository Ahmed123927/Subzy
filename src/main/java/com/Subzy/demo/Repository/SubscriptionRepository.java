package com.Subzy.demo.Repository;

import com.Subzy.demo.Enitiy.Subscription;
import com.Subzy.demo.Utils.SubscriptionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription,Long> {
    long countByPlanCompanyIdAndStatus(Long companyId, SubscriptionStatus subscriptionStatus);
}
