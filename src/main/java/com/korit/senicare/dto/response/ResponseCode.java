package com.korit.senicare.dto.response;

// ResponseDTO의 code 상수

// 약속과 관련된 내용
public interface ResponseCode {
    
    String SUCCESS = "SU";

    String VALIDATION_FAIL = "VF";
    String DUPLICATED_USER_ID = "DI";
    String DUPLICATED_TEL_NUMBER = "DT";

    String TEL_AUTH_FAIL = "TAF";
    String SIGN_IN_FAIL="SF";// 401 error
    
    String MESSAGE_SEND_FAIL = "TF";
    String TOKEN_CREATE_FAIL = "TCF"; // 500 Error 
    String DATABASE_ERROR = "DBE";
}
