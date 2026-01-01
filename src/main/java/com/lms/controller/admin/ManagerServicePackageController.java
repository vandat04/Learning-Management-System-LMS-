package com.lms.controller.admin;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.dto.request.common.AdminCreateUpdatePlanRequest;
import com.lms.entity.common.InstructorSubscription;
import com.lms.entity.common.PageResponse;
import com.lms.entity.common.SubscriptionPlan;
import com.lms.service.core.common.AdminManageSubscriptionService;
import com.lms.util.PageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/service-package")
@RequiredArgsConstructor
public class ManagerServicePackageController {

    private final AdminManageSubscriptionService adminManageSubscriptionPlanService;

    @PostMapping("/add")
    public ResponseEntity<?> createNewPlan(@Valid @RequestBody AdminCreateSubscriptionPlanRequest request) {
        adminManageSubscriptionPlanService.createNewPlan(request);
        return ResponseEntity.ok("Create plan successfully");
    }

    @GetMapping("/view-plan")
    public PageResponse<SubscriptionPlan> viewAllPlan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<SubscriptionPlan> list = adminManageSubscriptionPlanService.getSubscriptionPlanByAdmin();
        return PageUtil.paginate(list, page, size);
    }

    @GetMapping("view-plan/{planId}")
    public ResponseEntity<?> getPlanDetail(@PathVariable Integer planId) {
        SubscriptionPlan response = adminManageSubscriptionPlanService.getSubscriptionDetailByAdmin(planId);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get plan successfully",
                        "data", response
                )
        );
    }

    @PutMapping("view-plan/{planId}/update")
    public ResponseEntity<?> updatePlan(@PathVariable Integer planId, @Valid @RequestBody AdminCreateUpdatePlanRequest request){
        adminManageSubscriptionPlanService.updateSubscriptionPlanByAdmin(planId, request);
        return ResponseEntity.ok("Update subscription successfully");
    }

    @GetMapping("view-plan/active/{isActive}")
    public PageResponse<?> getPlanByActiveFilter(
            @PathVariable Boolean isActive,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size)
    {
        List<SubscriptionPlan> list = adminManageSubscriptionPlanService.getSubscriptionPlanByActiveFilter(isActive);
        return PageUtil.paginate(list, page, size);
    }

    @GetMapping("/view-plan/duration/from={from}&to={to}")
    private PageResponse<?> getPlanByDurian(
            @PathVariable Double from,
            @PathVariable Double to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        List<SubscriptionPlan> list = adminManageSubscriptionPlanService.getSubscriptionPlanByDayFilter(from, to);
        return PageUtil.paginate(list, page, size);
    }

}
