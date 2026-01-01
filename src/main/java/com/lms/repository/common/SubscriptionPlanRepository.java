package com.lms.repository.common;

import com.lms.entity.common.SubscriptionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SubscriptionPlanRepository extends JpaRepository<SubscriptionPlan, Integer> {
    List<SubscriptionPlan> findByIsActive(Boolean isActive);
    boolean existsById(Integer planId);
    List<SubscriptionPlan> findByDurationMonthsBetween(Integer from, Integer to);
}
