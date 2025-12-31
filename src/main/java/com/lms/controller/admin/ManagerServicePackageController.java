package com.lms.controller.admin;

import com.lms.dto.request.common.AdminCreateSubscriptionPlanRequest;
import com.lms.entity.common.PageResponse;
import com.lms.entity.common.SubscriptionPlan;
import com.lms.service.core.common.AdminManageSubscriptionPlanService;
import com.lms.util.PageUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/service-package")
@RequiredArgsConstructor
public class ManagerServicePackageController {

    private final AdminManageSubscriptionPlanService adminManageSubscriptionPlanService;

    @PostMapping("/add")
    public ResponseEntity<?> createNewPlan(@Valid @RequestBody AdminCreateSubscriptionPlanRequest request){
        adminManageSubscriptionPlanService.createNewPlan(request);
        return ResponseEntity.ok("Create plan successfully");
    }

    @GetMapping("/view-plan")
    public PageResponse<SubscriptionPlan> createNecPlan(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10")  int size
    ){
        List<SubscriptionPlan> list = adminManageSubscriptionPlanService.getSubscriptionPlanByAdmin();
        return PageUtil.paginate(list,page,size);
    }

}
