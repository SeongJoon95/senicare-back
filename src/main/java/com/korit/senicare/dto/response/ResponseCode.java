package com.korit.senicare.dto.response;

// ResponseDTO의 code 상수

// 약속과 관련된 내용
public interface ResponseCode {
    
    // 200 OK
    String SUCCESS = "SU";

    // 400 Error
    String VALIDATION_FAIL = "VF";
    String DUPLICATED_USER_ID = "DI";
    String DUPLICATED_TEL_NUMBER = "DT";
    String NO_EXIST_USER_ID = "NI";
    String NO_EXIST_TOOL = "NT";

    // 401 Error
    String TEL_AUTH_FAIL = "TAF";
    String SIGN_IN_FAIL="SF"; 
    String AUTHENTICATION_FAIL = "AF";

    // 500 Error
    String MESSAGE_SEND_FAIL = "TF";
    String TOKEN_CREATE_FAIL = "TCF";  
    String DATABASE_ERROR = "DBE";
}
