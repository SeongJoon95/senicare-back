package com.korit.senicare.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    // MultipartFile: 사용자가 업로드한 File을 핸들러에서 손쉽게 다룰 수 있게 도와주는 매개변수 중 하나.
    String upload(MultipartFile file); // 클라이언트가 파일 업로드 할때 필요한 용도로 만든 메서드

    // Resource: 파일을 다루는 데 있어서 매우 유용한 추상화 레이어를 제공하여 다양한 환경에서 쉽게 파일을 다룰 수 있게 해줌
    Resource getFile(String fileName); // 클라이언트가 파일 업로드 할때 필요한 파일 이름 메서드
}
