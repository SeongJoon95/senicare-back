package com.korit.senicare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.senicare.entity.TelAuthNumberEntity;

@Repository
// JpaRepository<받을 class엔티티,pk타입>
public interface TelAuthNumberRepository extends JpaRepository<TelAuthNumberEntity, String> {
    
    boolean existsByTelNumberAndAuthNumber(String telNumber, String authNumber);
}
