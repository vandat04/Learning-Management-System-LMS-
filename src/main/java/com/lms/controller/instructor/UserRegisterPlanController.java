package com.lms.controller.instructor;

import com.lms.entity.common.PageResponse;
import com.lms.entity.common.SubscriptionPlan;
import com.lms.service.core.common.SubscriptionPlanService;
import com.lms.service.core.payment.PaymentService;
import com.lms.util.PageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/service-package")
@RequiredArgsConstructor
public class UserRegisterPlanController {

    private final SubscriptionPlanService subscriptionPlanService;
    private final PaymentService paymentService;

    @GetMapping("/view")
    public PageResponse<?> viewAllPlan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        List<SubscriptionPlan> list = subscriptionPlanService.getSubscriptionPlan();
        return PageUtil.paginate(list, page, size);
    }

    @GetMapping("/view/duration/from={from}&to={to}")
    private PageResponse<?> getPlanByDurian(
            @PathVariable Double from,
            @PathVariable Double to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        List<SubscriptionPlan> list = subscriptionPlanService.getSubscriptionPlanByDayFilter(from, to, true);
        return PageUtil.paginate(list, page, size);
    }

    @GetMapping("/view/{planId}")
    public ResponseEntity<?> getPlanDetail(@PathVariable Integer planId) {
        SubscriptionPlan response = subscriptionPlanService.getSubscriptionDetail(planId);
        return ResponseEntity.ok(
                Map.of(
                        "message", "Get plan successfully",
                        "data", response
                )
        );
    }

    @GetMapping("/view/search/name={name}")
    private PageResponse<?> searchPlanByName(
            @PathVariable String name,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){
        List<SubscriptionPlan> list = subscriptionPlanService.findSubscriptionPlanByName(name);
        return PageUtil.paginate(list, page, size);
    }

    @PostMapping("/payment/create/planId={planId}")
    public CreatePaymentLinkResponse payment(
            @PathVariable Integer planId
    ){
        return paymentService.createSubscriptionPlanPayment(planId);
    }

    @PostMapping("/payment/payos/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody String request) {
        paymentService.handleSubPayOSWebhook(request);
        return ResponseEntity.ok("Payment oke!!!");
    }

    @GetMapping("/payment/success")
    public ResponseEntity<?> paymentSuccess(){
        return ResponseEntity.ok("Payment Successfully!!!");
    }

    @GetMapping("/payment/cancel")
    public ResponseEntity<?> handleCancel(@RequestParam Long orderCode) {
        paymentService.handleCancelPayment(orderCode);
        return ResponseEntity.ok("Payment Fail!!!");
    }

}
