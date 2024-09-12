package com.korit.senicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.NurseEntity;

@Repository
public interface NurseRepository extends JpaRepository<NurseEntity,String>{
    
    // userId가 존재하는지 아닌지 찾게됨
    boolean existsByUserId(String userId);
    boolean existsByTelNumber(String telNumber);

    // 유저 아이디를 가지고 조회
    NurseEntity findByUserId(String userId);
    
}