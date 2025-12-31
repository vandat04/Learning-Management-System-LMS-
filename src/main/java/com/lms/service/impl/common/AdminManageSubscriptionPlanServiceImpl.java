package com.lms.service.impl.common;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.entity.common.SubscriptionPlan;
import com.lms.repository.common.SubscriptionPlanRepository;
import com.lms.service.core.common.AdminManageSubscriptionPlanService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManageSubscriptionPlanServiceImpl implements AdminManageSubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final Validate validate;

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByAdmin() {
        return subscriptionPlanRepository.findAll();
    }

    @Override
    public void createNewPlan(AdminCreateSubscriptionPlanRequest request) {
        //Check
        String name = request.getName();
        Integer durationMonth = request.getDurationMonth();
        BigDecimal price = request.getPrice();

        validate.checkNull(name);
        validate.validateBio(name);
        validate.validateByAI(name);

        validate.checkNull(durationMonth);
        validate.isInteger(price);

        SubscriptionPlan subscriptionPlan = new SubscriptionPlan();
        subscriptionPlan.setName(name);
        subscriptionPlan.setDurationMonths(durationMonth);
        subscriptionPlan.setPrice(price);
        subscriptionPlan.setIsActive(true);

        subscriptionPlanRepository.save(subscriptionPlan);
    }
}
