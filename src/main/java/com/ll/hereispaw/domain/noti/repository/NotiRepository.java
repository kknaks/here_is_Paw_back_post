package com.ll.hereispaw.domain.noti.repository;

import com.ll.hereispaw.domain.noti.entity.Noti;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotiRepository extends JpaRepository<Noti, Long> {

}
