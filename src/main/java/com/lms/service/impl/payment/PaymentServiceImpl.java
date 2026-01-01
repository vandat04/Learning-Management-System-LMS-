package com.lms.service.impl.payment;

import com.lms.entity.common.SubscriptionPlan;
import com.lms.entity.common.SubscriptionTime;
import com.lms.entity.payment.Payment;
import com.lms.entity.payment.PaymentItem;
import com.lms.exception.AppException;
import com.lms.repository.common.SubscriptionPlanRepository;
import com.lms.repository.common.SubscriptionTimeRepository;
import com.lms.repository.payment.PaymentItemRepository;
import com.lms.repository.payment.PaymentRepository;
import com.lms.service.core.payment.PaymentService;
import com.lms.util.Util;
import com.lms.util.Validate;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.webhooks.WebhookData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PayOS payOS;
    private final PaymentRepository paymentRepository;
    private final PaymentItemRepository paymentItemRepository;
    private final SubscriptionPlanRepository subscriptionPlanRepository;
    private final SubscriptionTimeRepository subscriptionTimeRepository;
    private final Util util;
    private final Validate validate;

    long expiredAt = System.currentTimeMillis() / 1000L + 5 * 60L;

    @org.springframework.beans.factory.annotation.Value("${payos.returnUrl}")
    private String returnUrl;
    @org.springframework.beans.factory.annotation.Value("${payos.cancelUrl}")
    private String cancelUrl;

    @Transactional
    @Override
    public CreatePaymentLinkResponse createSubscriptionPlanPayment(Integer planId) {
        //        // 1. Tạo payment
        //        // 2. Add items (giả sử coursePrice = 100k)
        //        // 3. Gọi PayOS
        //        Long orderCode = System.currentTimeMillis();
        SubscriptionPlan plan = subscriptionPlanRepository.findByIdAndIsActive(planId, true);
        if (plan == null) {
            throw new AppException("The subscription plan not exist", HttpStatus.BAD_REQUEST);
        }
        //Check login + role = 2 mới cho phép mua
        Integer userId = util.getUserId();
        if (util.checkUserRole()) {
            throw new AppException("Please update your account become instructor!!", HttpStatus.BAD_REQUEST);
        }
        if (!util.checkInstructorRole()) {
            throw new AppException("Please login!!", HttpStatus.BAD_REQUEST);
        }
        //Tạo order code
        Long orderCode = System.currentTimeMillis();

        Payment payment = new Payment();
        PaymentItem paymentItem = new PaymentItem();

        Integer paymentId;

        // Tạo request thanh toán
        try {
            CreatePaymentLinkRequest payReq = CreatePaymentLinkRequest.builder()
                    .orderCode(orderCode)
                    .amount(plan.getPrice().longValue())
                    .description("Buy Subscription Plan")
                    .returnUrl(returnUrl)
                    .cancelUrl(cancelUrl)
                    .expiredAt(expiredAt)
                    .build();

            //Create payment
            payment.setUserId(userId);
            payment.setAmount(plan.getPrice());
            payment.setPaymentType(2);
            payment.setOrderCode(orderCode);
            payment.setStatus(1); //PENDING
            payment.setCreatedAt(LocalDateTime.now());
            paymentId = paymentRepository.save(payment).getId();

            //Create payment item
            paymentItem.setPaymentId(paymentId);
            paymentItem.setPlanId(planId);
            paymentItem.setPrice(plan.getPrice());
            paymentItemRepository.save(paymentItem);

            return payOS.paymentRequests().create(payReq);
        } catch (Exception e) {
            validate.saveError(6);
            throw new AppException("Payment system error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void handleSubPayOSWebhook(String request) {
        //1. Verify data lấy ra thông tin đơn hàng: orderCode, status
        //2. Tìm payment theo orderCode
        //3. Chống webhook bị gọi lại
        //4. Nếu là PAID thì lưu lưu thêm các thông tin PayOS và Payment
        //5. Thêm người dùng vào Enrrorment
        //6. Nếu người dùng không thanh toán thì lưu thành 4, 5

        // Xác thực webhook
        try {
            // Xác thực webhook
            WebhookData data = payOS.webhooks().verify(request);

            Long orderCode = data.getOrderCode();
            String transactionCode = data.getReference();
            String invoiceId = data.getPaymentLinkId();
            String paidAt = data.getTransactionDateTime();

            // Xử lý trạng thái thanh toán
            Payment payment = paymentRepository.findByOrderCode(orderCode);
            if (payment.getStatus() != null && payment.getStatus() == 2) {
                System.out.println("Webhook ignored: payment already PAID");
                return;
            }

            payment.setTransactionCode(transactionCode);
            payment.setInvoiceId(invoiceId);
            payment.setPaidAt(LocalDateTime.parse(paidAt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            if ("00".equals(data.getCode())) {
                payment.setStatus(2); // PAID
                paymentRepository.save(payment);
                handleSubscriptionTime(payment);
            } else {
                payment.setStatus(5); // UNKNOWN / ERROR
                paymentRepository.save(payment);
            }

        } catch (Exception e) {
            validate.saveError(6);
            throw new AppException("Payment system error", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void handleCancelPayment(Long orderCode) {
        Payment payment = paymentRepository.findByOrderCode(orderCode);
        payment.setStatus(3);
        paymentRepository.save(payment);
    }

    @Override
    public void handleQRExpiredPayments() {
        LocalDateTime now = LocalDateTime.now().minusMinutes(5);

        List<Payment> expiredPayments =
                paymentRepository.findPendingExpired(now);
        if (!expiredPayments.isEmpty()) {
            for (Payment p : expiredPayments) {
                p.setStatus(5); // EXPIRED
                paymentRepository.save(p);
            }
        }
    }

    public void handleSubscriptionTime(Payment payment) {
        Integer userId = payment.getUserId();
        List<PaymentItem> paymentItems = paymentItemRepository.findByPaymentId(payment.getId());

        int total = 0;
        for (PaymentItem item : paymentItems) {
            SubscriptionPlan subscriptionPlan = subscriptionPlanRepository.findById(item.getPlanId()).orElse(null);
            if (subscriptionPlan == null) {
                total += 0;
            } else {
                System.out.println();
                total += subscriptionPlan.getDurationDays();
            }
        }
        SubscriptionTime subscriptionTime = subscriptionTimeRepository.findById(userId).orElse(null);
        if (subscriptionTime == null) {
            subscriptionTime = new SubscriptionTime();
            subscriptionTime.setInstructorId(userId);
        }

        if (subscriptionTime.getEndDate() == null || subscriptionTime.getEndDate().isBefore(LocalDateTime.now())) {
            subscriptionTime.setEndDate(LocalDateTime.now().plusDays(total));
        } else {
            subscriptionTime.setEndDate(subscriptionTime.getEndDate().plusDays(total));
        }

        subscriptionTimeRepository.save(subscriptionTime);
    }

}
