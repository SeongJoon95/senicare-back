package com.korit.senicare.dto.response.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.korit.senicare.dto.response.ResponseCode;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.ResponseMessage;

import lombok.Getter;

@Getter
// ResponseDto + 추가되는 애들만 사용하기 위해 만든 클래스
// Lombok을 사용 x 내부에서만 사용할수 있게 하기 위해 
public class SignInResponseDto extends ResponseDto {
    
    private String accessToken;
    private Integer expiration;

    // 빈 생성자가 없을 경우 자식 클래스의 첫번째 메소드에 부모 생성자를 호출 해줘야함 "super"를 통해
    private SignInResponseDto(String accessToken) {

        super(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        this.accessToken = accessToken;
        this.expiration = 10*60*60; // 10시간을 초단위로 변경했을때 공식 .. jwtprovider에 있는 숫자와 일치해야함 '10'

    }

    public static ResponseEntity<SignInResponseDto> success(String accssToken) {
        SignInResponseDto responseBody = new SignInResponseDto(accssToken);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

}
