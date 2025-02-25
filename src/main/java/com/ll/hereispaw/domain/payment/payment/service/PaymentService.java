package com.ll.hereispaw.domain.payment.payment.service;

import com.ll.hereispaw.domain.payment.payment.entity.Payment;
import com.ll.hereispaw.domain.payment.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    // 결제 후 response data를 DB에 저장
    @Transactional
    public Payment savePaymentData(JSONObject responseData) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        LocalDateTime requestedAt = LocalDateTime.parse((String) responseData.get("requestedAt"), formatter);
        LocalDateTime approvedAt = LocalDateTime.parse((String) responseData.get("approvedAt"), formatter);

        JSONObject easyPay = (JSONObject) responseData.get("easyPay");
        Integer discountAmount = 0;
        if (easyPay != null) {
            discountAmount = ((Long) easyPay.get("discountAmount")).intValue();
        }

        JSONObject card = (JSONObject) responseData.get("card");
        Integer amount = 0;
        if (card != null) {
            amount = ((Long) card.get("amount")).intValue();
        }

        Payment payment = Payment.builder()
                .paymentKey((String) responseData.get("paymentKey"))
                .orderId((String) responseData.get("orderId"))
                .orderName((String) responseData.get("orderName"))
                .status((String) responseData.get("status"))
                .method((String) responseData.get("method"))
                .totalAmount(((Long) responseData.get("totalAmount")).intValue())
                .discountAmount(discountAmount)
                .amount(amount)
                .requestedAt(requestedAt)
                .approvedAt(approvedAt)
                .build();

        return paymentRepository.save(payment);
    }
}
