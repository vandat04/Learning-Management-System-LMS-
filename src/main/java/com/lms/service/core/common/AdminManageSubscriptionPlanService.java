package com.lms.service.core.common;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.entity.common.SubscriptionPlan;

import java.util.List;

public interface AdminManageSubscriptionPlanService {
    List<SubscriptionPlan> getSubscriptionPlanByAdmin();
    void createNewPlan(AdminCreateSubscriptionPlanRequest request);
}
