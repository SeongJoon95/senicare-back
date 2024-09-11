package com.korit.senicare.dto.response;

// ResponseDTO의 code 상수

// 약속과 관련된 내용
public interface ResponseCode {
    
    String SUCCESS = "SU";

    String VALIDATION_FAIL = "VF";
    String DUPLICATED_USER_ID = "DI";

    String DATABASE_ERROR = "DBE";
}
