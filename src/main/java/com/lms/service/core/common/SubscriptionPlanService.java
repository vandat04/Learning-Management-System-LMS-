package com.lms.service.core.common;

import com.lms.entity.common.SubscriptionPlan;
import java.util.List;

public interface SubscriptionPlanService {
    List<SubscriptionPlan> getSubscriptionPlan();
    List<SubscriptionPlan> getSubscriptionPlanByActiveFilter(Boolean isActive);
    SubscriptionPlan getSubscriptionDetail(Integer planId);
    List<SubscriptionPlan> getSubscriptionPlanByDayFilter(Double from, Double to, Boolean isActive);
    List<SubscriptionPlan> findSubscriptionPlanByName(String name);
}
