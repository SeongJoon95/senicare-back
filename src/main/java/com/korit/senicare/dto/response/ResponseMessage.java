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
    String NO_EXIST_TOOL = "No exist tool.";
    String NO_EXIST_CUSTOMER= "No exist customer.";
    String TOOL_INSUFFICIENT = "This tool is insufficient in number.";

    // 401 Error [로그인실패]
    String TEL_AUTH_FAIL = "Tel number authentication failed.";
    String SIGN_IN_FAIL= "Sign in failed."; 
    String AUTHENTICATION_FAIL = "Authentication fail";

    // 403 Error [권한없음]
    String No_PERMISSION = "No permission.";

    // 500 Error
    String MESSAGE_SEND_FAIL = "Auth number send failed.";
    String TOKEN_CREATE_FAIL = "Token creation failed.";
    String DATABASE_ERROR = "Database error.";

} 
