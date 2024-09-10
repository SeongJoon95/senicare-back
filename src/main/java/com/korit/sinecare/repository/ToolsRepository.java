package com.korit.sinecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.sinecare.entity.ToolsEntity;

@Repository
public interface ToolsRepository extends JpaRepository<ToolsEntity, Integer>{
    
} 
