package com.ll.hereispaw.domain.member.mypet.repository;

import com.ll.hereispaw.domain.member.member.entity.Member;
import com.ll.hereispaw.domain.member.mypet.entity.MyPet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MyPetRepository extends JpaRepository<MyPet, Long> {
    List<MyPet> findAllByMember(Member loginUser);
}
