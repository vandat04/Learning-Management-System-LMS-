package com.lms.service.core.common;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.dto.request.common.AdminCreateUpdatePlanRequest;
import com.lms.entity.common.SubscriptionPlan;
import java.util.List;

public interface AdminManageSubscriptionService {
    // Manage Subscription Plan
    List<SubscriptionPlan> getSubscriptionPlanByAdmin();
    void createNewPlan(AdminCreateSubscriptionPlanRequest request);
    SubscriptionPlan getSubscriptionDetailByAdmin(Integer planId);
    List<SubscriptionPlan> getSubscriptionPlanByActiveFilter(Boolean active);
    void updateSubscriptionPlanByAdmin(Integer planId, AdminCreateUpdatePlanRequest request);
    List<SubscriptionPlan> getSubscriptionPlanByDayFilter(Double from, Double to);
}
