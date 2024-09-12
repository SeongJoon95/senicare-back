package com.korit.senicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.CareRecordEntity;

@Repository
// JpaRepository : JPA가 제공하는 인터페이스 중 하나로 CRUD 작업을 처리하는 메서드들이
// 이미 내장하고 있어 데이터 관리 작업을 좀 더 편리하게 처리할 수 있음.
public interface CareRecordRepository extends JpaRepository<CareRecordEntity, Integer>{
    
}