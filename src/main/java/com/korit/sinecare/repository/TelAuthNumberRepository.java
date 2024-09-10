package com.korit.sinecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.sinecare.entity.TelAuthNumberEntity;

@Repository
// JpaRepository<받을 class엔티티,pk타입>
public interface TelAuthNumberRepository extends JpaRepository<TelAuthNumberEntity, String> {
    
}
