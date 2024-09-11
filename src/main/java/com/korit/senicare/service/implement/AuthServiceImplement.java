package com.korit.senicare.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.repository.NurseRepository;
import com.korit.senicare.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service // 스프링 빈으로 만드는 어노테이션 [/ component]
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{
    private final NurseRepository nurseRepository;

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {
        
        String userId = dto.getUserId();

        try{
            boolean isExistedId = nurseRepository.existsByUserId(userId);
            // if(isExistedId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("DI","Duplicated user id"));
            if(isExistedId) return ResponseDto.duplicatedUserId();

        } catch(Exception e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DBE","Database error"));
            return ResponseDto.databaseError();

        }

        // return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("SU","Success."));
        return ResponseDto.success();
    }

}
