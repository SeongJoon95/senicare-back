package com.korit.senicare.service;

import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.response.nurse.GetNurseListResponseDto;
import com.korit.senicare.dto.response.nurse.GetSignInResponseDto;

public interface NurseService {

    ResponseEntity<? super GetNurseListResponseDto> getNurseList();
    ResponseEntity<? super GetSignInResponseDto> getSignIn(String userId);
    
}
