package com.korit.sinecare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.korit.sinecare.entity.NurseEntity;

@Repository
public interface NurseRepository extends JpaRepository<NurseEntity,String>{

    
}