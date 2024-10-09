package com.korit.senicare.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.request.auth.SignInRequestDto;
import com.korit.senicare.dto.request.auth.SignUpRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.auth.SignInResponseDto;
import com.korit.senicare.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor // 클래스의 final로 선언된 필드 (authService)를 자동으로 생성자를 통해 주입받습니다.
public class AuthController {
    
    // AuthService : 인증 관련 비즈니스 로직을 처리하는 서비스 클래스. 아이디 중복 체크 로직을 authService가 처리
    private final AuthService authService;

    @PostMapping("/id-check")
    // ResponseEntity<ResponseDto> : 메서드의 반환타입. 이 메서드는 HTTP 응답(ResponseEntity)로 래핑된 ResponseDto 객체를 반환
    // ResponseEntity : HTTP 상태 코드와 헤더, 본문을 포함한 응답을 나타냄.
    // ResponseDto : 응답 데이터를 담는 객체로, 응답 상태와 메시지를 포함할 수 있음.
    public ResponseEntity<ResponseDto> idCheck(
        // @RequestBody를 통해서 URL의 body의 데이터를 받아 IdCheckRequestDto 객체로 변환
        // @Valid : IdCheckRequestDto 클래스 내의 유효성 검사를 하기위한 어노테이션
        @RequestBody @Valid IdCheckRequestDto requestBody
    ) {
        // authService.idCheck(requestBody) -> HTTP 응답을 생성하여 클라이언트에 반환
        // ResponseDto : 응답데이터를 감싸는 dto
        ResponseEntity<ResponseDto> response = authService.idCheck(requestBody);
        return response;
    }

    @PostMapping("/tel-auth")
    public ResponseEntity<ResponseDto> telAuth(
        @RequestBody @Valid TelAuthRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = authService.telAuth(requestBody);
        return response;
    }

    @PostMapping("/tel-auth-check")
    public ResponseEntity<ResponseDto> telAuthCheck(
        @RequestBody @Valid TelAuthCheckRequestDto requestBody
    ) {
        ResponseEntity<ResponseDto> response = authService.telAuthCheck(requestBody);
        return response;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<ResponseDto> signUp(
        @RequestBody @Valid SignUpRequestDto requestBody
    ) {
        // authService의 signUP을 불러와서 클라이언트로부터 받은 값을을 넣어준다.
        ResponseEntity<ResponseDto> response = authService.signUp(requestBody);
        return response;
    }

    // 로그인 관련 메서드
    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signIn(
        @RequestBody @Valid SignInRequestDto requestBody
    ) {
        ResponseEntity<? super SignInResponseDto> response = authService.signIn(requestBody);
        return response;
    }
}
