package com.korit.senicare.service;

import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.request.auth.SignInRequestDto;
import com.korit.senicare.dto.request.auth.SignUpRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.auth.SignInResponseDto;

public interface AuthService {
    
    ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto);
    ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto);
    ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto);
    ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto);
    // 성공과 실패로 나뉘기 때문 == 클래스가 달라질수 있으므로 여러 타입을 받을수 있는 와일드 카드를 사용해야함
    // 자식 테이블에서 상속 받아 하는 이유 -> 부모 테이블에서는 많은 자식 클래스가 있으므로 가독성이 떨어짐
    // 자식 테이블에서 해야 가독성이 좋음
    ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
}
