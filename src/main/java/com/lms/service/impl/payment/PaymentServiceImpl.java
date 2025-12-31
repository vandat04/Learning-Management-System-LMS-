package com.lms.service.impl.payment;

import com.lms.repository.payment.PaymentItemRepository;
import com.lms.repository.payment.PaymentRepositpry;
import com.lms.service.core.payment.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.payos.PayOS;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkRequest;
import vn.payos.model.v2.paymentRequests.CreatePaymentLinkResponse;
import vn.payos.model.webhooks.WebhookData;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PayOS payOS;
    private final PaymentRepositpry paymentRepositpry;
    private final PaymentItemRepository paymentItemRepository;

    long expiredAt = System.currentTimeMillis() / 1000L + 5 * 60L;

    @Override
    public CreatePaymentLinkResponse createPaymentTest() {

        // 1. Tạo payment
        // 2. Add items (giả sử coursePrice = 100k)
        // 3. Gọi PayOS
        Long orderCode = System.currentTimeMillis();

        // Tạo request thanh toán (giả lập)
        CreatePaymentLinkRequest payReq = CreatePaymentLinkRequest.builder()
                .orderCode(orderCode)
                .amount(2000L) // ví dụ 2000 đồng
                .description("Thanh toán khóa học test")
                .returnUrl("https://22274acfc028.ngrok-free.app/payment/success")
                .cancelUrl("https://22274acfc028.ngrok-free.app/payment/fail")
                .expiredAt(expiredAt)
                .build();

        // Gọi PayOS tạo link thanh toán
        return payOS.paymentRequests().create(payReq);
    }

    @Override
    public void handlePayOSWebhook(String request) {
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
            String status = data.getCode();
            String transactionId = data.getReference();

            // Log kiểm tra
            System.out.println("==== PAYOS WEBHOOK RECEIVED ====");
            System.out.println("OrderCode: " + orderCode);
            System.out.println("Status: " + status);
            System.out.println("TransactionId: " + transactionId);

            // Xử lý trạng thái thanh toán
            switch (status) {
                case "PAID" -> System.out.println("Payment successful for order " + orderCode);
                case "CANCELED", "EXPIRED" -> System.out.println("Payment failed or expired for order " + orderCode);
                default -> System.out.println("Unknown status: " + status);
            }

        } catch (Exception e) {
            System.err.println("Webhook verification failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
