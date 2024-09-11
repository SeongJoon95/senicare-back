package com.korit.senicare.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 중복되는 코드 -> 유지보수가 힘들어 지기 때문에 -> 무엇을 리턴하는지 한눈에 보기 힘들기 때문에
// 메서드 함수를 이용하여 보기 편하게 하기 위하여 만든 클래스
// 미리 만들어두고 호출하기 위한 용도로 사용

@Getter
@AllArgsConstructor
public class ResponseDto {
    
    private String code;
    private String message;

    public static ResponseEntity<ResponseDto> success() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responsBody);
    }

    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsBody);
    }

    public static ResponseEntity<ResponseDto> duplicatedUserId() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsBody);
    }

    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }

}
