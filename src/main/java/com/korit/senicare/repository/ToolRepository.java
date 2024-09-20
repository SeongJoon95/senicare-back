package com.korit.senicare.repository;

import java.util.List;

// import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.ToolEntity;


@Repository
// JpaRepository : CRUD 작업을 처리하는 메서드들을 이미 내장하고 있어 데이터 관리 작업을 좀 더 편리하게 처리할 수 있음
public interface ToolRepository extends JpaRepository<ToolEntity, Integer>{
    
    // toolNumber 기준으로 조회할수 있게 해줌
    ToolEntity findByToolNumber(Integer toolNumber);
    List<ToolEntity> findByOrderByToolNumberDesc();
} 
