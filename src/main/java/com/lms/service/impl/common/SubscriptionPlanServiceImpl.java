package com.lms.service.impl.common;

import com.lms.entity.common.SubscriptionPlan;
import com.lms.exception.AppException;
import com.lms.repository.common.SubscriptionPlanRepository;
import com.lms.service.core.common.SubscriptionPlanService;
import com.lms.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscriptionPlanServiceImpl implements SubscriptionPlanService {

    private final SubscriptionPlanRepository subscriptionRepository;
    private final Util util;

    @Override
    public List<SubscriptionPlan> getSubscriptionPlan() {
        if (util.checkAdminRole()){
            return subscriptionRepository.findAll();
        } else {
            return getSubscriptionPlanByActiveFilter(true);
        }
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByActiveFilter(Boolean isActive) {
        return subscriptionRepository.findByIsActive(isActive);
    }

    @Override
    public SubscriptionPlan getSubscriptionDetail(Integer planId) {
        if (util.checkAdminRole()){
            return subscriptionRepository.findById(planId).orElseThrow(()
                    -> new AppException("Can not find plan by your id", HttpStatus.BAD_REQUEST));
        } else {
            System.out.println(planId);
            SubscriptionPlan plan = subscriptionRepository.findByIdAndIsActive(planId, true);
            if (plan == null){
                throw new AppException("Can not find plan by your id", HttpStatus.BAD_REQUEST);
            }
            return plan;
        }
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByDayFilter(Double from, Double to, Boolean isActive) {
        if (from == null) from = 0.0;
        if (to == null) to = 0.0;

        int f = (int) Math.floor(from);
        int t = (int) Math.ceil(to);

        if (t < f ){
            Integer temp = t;
            t = f;
            f = temp;
        }
        return subscriptionRepository.findByDurationDaysBetweenAndIsActive(f, t, isActive);
    }

    @Override
    public List<SubscriptionPlan> findSubscriptionPlanByName(String name) {
        if (util.checkAdminRole()){
            return subscriptionRepository.findByNameContaining(name);
        } else {
            return subscriptionRepository.findByNameContainingAndIsActive(name, true);
        }
    }

    @Override
    public List<SubscriptionPlan> getSubscriptionPlanByPriceFilter(Double from, Double to, Boolean isActive) {
        if (from == null) from = 0.0;
        if (to == null) to = 0.0;

        int f = (int) Math.floor(from);
        int t = (int) Math.ceil(to);

        if (t < f ){
            Integer temp = t;
            t = f;
            f = temp;
        }
        return subscriptionRepository.findByPriceBetweenAndIsActive(f, t, isActive);
    }


}
