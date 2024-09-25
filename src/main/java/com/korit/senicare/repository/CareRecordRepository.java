package com.korit.senicare.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.CareRecordEntity;

import jakarta.transaction.Transactional;

@Repository
// JpaRepository : JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들이
// 이미 내장하고 있어 데이터 관리 작업을 좀 더 편리하게 처리할 수 있음.
public interface CareRecordRepository extends JpaRepository<CareRecordEntity, Integer>{
    
    List<CareRecordEntity> findByCustomerNumberOrderByRecordNumberDesc(Integer customerNumber);

    @Transactional // SQL의 Commit 같은 존재 / 변경된 사항을 저장하기 위해 사용하는 어노테이션
    void deleteByCustomerNumber(Integer customerNumber);

}