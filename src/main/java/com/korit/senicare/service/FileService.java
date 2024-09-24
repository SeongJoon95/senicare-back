package com.korit.senicare.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    
    String upload(MultipartFile file); // 클라이언트가 파일 업로드 할때 필요한 용도로 만든 메서드

    Resource getFile(String fileName); // 클라이언트가 파일 업로드 할때 필요한 파일 이름 메서드
}
