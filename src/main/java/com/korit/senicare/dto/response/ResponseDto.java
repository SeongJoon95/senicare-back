package com.korit.senicare.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 중복되는 코드 -> 유지보수가 힘들어 지기 때문에 -> 무엇을 리턴하는지 한눈에 보기 힘들기 때문에
// 메서드 함수를 이용하여 보기 편하게 하기 위하여 만든 클래스
// 미리 만들어두고 호출하기 위한 용도로 사용

@Getter
@AllArgsConstructor // 모든 매개변수를 받아오는 생성자만 존재 / 매개변수를 받아오지 않는 생성자는 없음
public class ResponseDto {
    
    private String code;
    private String message;

    // success() : 성공 응답을 반환(HTTP 200 OK)
    public static ResponseEntity<ResponseDto> success() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS);
        return ResponseEntity.status(HttpStatus.OK).body(responsBody);
    }

    // validationFail() : 검증 실패 응답을 반환 (HTTP 400 Bad Request)
    public static ResponseEntity<ResponseDto> validationFail() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsBody);
    }

    // duplicatedUserId() : 중복된 사용자 ID 응답을 반환 (HTTP 400 Bad Request)
    public static ResponseEntity<ResponseDto> duplicatedUserId() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.DUPLICATED_USER_ID, ResponseMessage.DUPLICATED_USER_ID);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsBody);
    }
    
    // duplicatedTelNumber() : 중복된 전화번호 응답을 반환 (HTTP 400 Bad Request)
    public static ResponseEntity<ResponseDto> duplicatedTelNumber() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.DUPLICATED_TEL_NUMBER, ResponseMessage.DUPLICATED_TEL_NUMBER);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responsBody);
    }
    
    // telAuthFail() : 전화 인증 실패 응답을 반환 (HTTP 401 Unauthorized)
    public static ResponseEntity<ResponseDto> telAuthFail() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.TEL_AUTH_FAIL, ResponseMessage.TEL_AUTH_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsBody);
    }
    
    // telAuthFail() : 로그인 실패 응답을 반환 (HTTP 401 Unauthorized)
    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsBody);
    }

    // messageDendFail() : 메시지 전송 실패 응답을 반환 (HTTP 500 Internal Server Error)
    public static ResponseEntity<ResponseDto> messageDendFail() {
    ResponseDto responseBody = new ResponseDto(ResponseCode.MESSAGE_SEND_FAIL, ResponseMessage.MESSAGE_SEND_FAIL);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
    
    // databaseError() : 데이터베이스 오류 응답을 반환.(HTTP 500 Internal Server Error)
    public static ResponseEntity<ResponseDto> databaseError() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
    }
    

}
