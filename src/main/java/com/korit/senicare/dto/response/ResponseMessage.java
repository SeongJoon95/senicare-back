package com.korit.senicare.dto.response;

// ResponseDTO의 message 상수

public interface ResponseMessage {
    
    // 200 OK
    String SUCCESS = "Success.";

    // 400 Error
    String VALIDATION_FAIL = "Validation failed.";
    String DUPLICATED_USER_ID = "Duplicated user id.";
    String DUPLICATED_TEL_NUMBER = "Duplicated user tel number.";
    String NO_EXIST_USER_ID = "No exist user id";

    // 401 Error [로그인실패]
    String TEL_AUTH_FAIL = "Tel number authentication failed.";
    String SIGN_IN_FAIL= "Sign in failed."; 
    String AUTHENTICATION_FAIL = "Authentication fail";

    // 500 Error
    String MESSAGE_SEND_FAIL = "Auth number send failed.";
    String TOKEN_CREATE_FAIL = "Token creation failed.";
    String DATABASE_ERROR = "Database error.";

} 
