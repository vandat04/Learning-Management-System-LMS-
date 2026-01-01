package com.lms.service.core.common;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.dto.request.common.AdminCreateUpdatePlanRequest;

public interface AdminManageSubscriptionService {
    // Manage Subscription Plan
    void createNewPlan(AdminCreateSubscriptionPlanRequest request);
    void updateSubscriptionPlanByAdmin(Integer planId, AdminCreateUpdatePlanRequest request);
}
