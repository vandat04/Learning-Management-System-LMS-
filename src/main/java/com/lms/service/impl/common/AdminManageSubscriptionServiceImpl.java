package com.lms.service.impl.common;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.dto.request.common.AdminCreateUpdatePlanRequest;
import com.lms.entity.common.SubscriptionPlan;
import com.lms.exception.AppException;
import com.lms.repository.common.InstructorSubscriptionRepository;
import com.lms.repository.common.SubscriptionPlanRepository;
import com.lms.service.core.common.AdminManageSubscriptionService;
import com.lms.util.Validate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminManageSubscriptionServiceImpl implements AdminManageSubscriptionService {

    private final SubscriptionPlanRepository subscriptionRepository;
    private final InstructorSubscriptionRepository instructorSubscriptionRepository;
    private final Validate validate;

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByAdmin() {
        return subscriptionRepository.findAll();
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

        subscriptionRepository.save(subscriptionPlan);
    }

    @Override
    public SubscriptionPlan getSubscriptionDetailByAdmin(Integer planId) {
        return subscriptionRepository.findById(planId).orElseThrow(()
                -> new AppException("Can not find plan by your id", HttpStatus.BAD_REQUEST));
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByActiveFilter(Boolean isActive) {
        return subscriptionRepository.findByIsActive(isActive);
    }

    @Override
    public void updateSubscriptionPlanByAdmin(Integer planId, AdminCreateUpdatePlanRequest request) {
        validate.checkNull(planId);

        SubscriptionPlan plan = subscriptionRepository.findById(planId).orElseThrow(()
            -> new AppException("Subscription plan is not exist!", HttpStatus.BAD_REQUEST));

        String name = request.getName().trim();
        Integer duration = request.getDurationMonth();
        BigDecimal price = request.getPrice();
        Boolean isActive = request.getIsActive();

        if (!name.isEmpty()){
            validate.validateBio(name);
            validate.validateByAI(name);
            plan.setName(name);
        }

        if (duration != null){
            plan.setDurationMonths(duration);
        }

        if (price != null){
            validate.isInteger(price);
            plan.setPrice(price);
        }

        if (isActive != null){
            plan.setIsActive(isActive);
        }

        subscriptionRepository.save(plan);
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByDayFilter(Double from, Double to) {
        Integer f = (int) Math.floor(from);
        Integer t = (int) Math.ceil(to);

        return subscriptionRepository.findByDurationMonthsBetween(f, t);
    }

}
