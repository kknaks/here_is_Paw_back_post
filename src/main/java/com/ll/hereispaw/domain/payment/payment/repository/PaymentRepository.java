package com.ll.hereispaw.domain.payment.payment.repository;

import com.ll.hereispaw.domain.payment.payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
