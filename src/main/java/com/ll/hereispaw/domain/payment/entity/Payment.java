package com.ll.hereispaw.domain.payment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    private String paymentKey;  // 결제 고유번호

    @Column(nullable = false)
    private String orderId;     // 주문 번호

    @Column(nullable = false)
    private String orderName;   // 주문명

    @Column(nullable = false)
    private String status;      // 결제 상태 (DONE, CANCELED 등)

    @Column(nullable = false)
    private String method;      // 결제 수단 (카드, 간편결제 등)

    @Column(nullable = false)
    private Integer totalAmount;     // 총 결제 금액

    @Column(nullable = false)
    private Integer discountAmount;  // 할인 금액

    @Column(nullable = false)
    private Integer amount;          // 실제 결제 금액

    @Column(nullable = false)
    private LocalDateTime requestedAt;   // 결제 요청 시각

    @Column(nullable = false)
    private LocalDateTime approvedAt;    // 결제 승인 시각
}
