package com.ll.hereispaw.domain.payment.payment.service;

import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.payment.payment.entity.Payment;
import com.ll.hereispaw.domain.payment.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    // 결제 후 포인트(amount)를 DB에 저장
    @Transactional
    public Payment savePaymentData(JSONObject responseData, Member member) {
        JSONObject card = (JSONObject) responseData.get("card");
        final Integer finalAmount = card != null ? ((Long) card.get("amount")).intValue() : 0;

        // 회원의 기존 payment 조회
        Payment payment = paymentRepository.findByMemberId(member.getId())
                .map(existingPayment -> {
                    // 기존 금액에 새로운 금액 추가
                    existingPayment.setAmount(existingPayment.getAmount() + finalAmount);
                    return existingPayment;
                })
                .orElseGet(() -> Payment.builder()
                        .member(member)
                        .amount(finalAmount)
                        .build());

        return paymentRepository.save(payment);
    }

    public Integer getPointsByMemberId(Long memberId) {
        return paymentRepository.findByMemberId(memberId)
                .map(Payment::getAmount)
                .orElse(0); // 결제 내역이 없으면 0 반환
    }
}
