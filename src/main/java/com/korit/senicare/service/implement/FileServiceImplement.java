package com.korit.senicare.service.implement;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.korit.senicare.service.FileService;

@Service
public class FileServiceImplement implements FileService{

    @Value("${file.path}")
    private String filePath; // value의 값을 저장함 / value의 값은 앱플리캐이션에 저장한 경로
    @Value("${file.url}")
    private String fileUrl;

    // 실제 업로드 작업 수행하는 메서드
    @Override
    public String upload(MultipartFile file) {
        
        // description: 빈 파일인지 확인 //
        if(file.isEmpty()) return null;

        // description: 원본 파일명 구하기 //
        String originalFileName = file.getOriginalFilename();
        // description: 확장자 구하기 //
        // ' . '의 마지막 인덱스 값을 찾겠다는 의미 = 파일의 속성명을 알 수 있기 때문
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); 
        // description: UUID 형식의 임의의 파일명 생성 //
        String uuid = UUID.randomUUID().toString(); // 고유한 식별을 하기위해 UUID 사용
        String saveFileName = uuid + extension;// 저장할 파일의 이름 = uuid+원본파일의 확장자
        // description: 파일이 저장될 경로(생성될 경로) //
        String savePath = filePath + saveFileName;
        
        // description: 파일 저장 [실제 파일 저장작업] //
        
        // 대비가 부족한 경우 -> 용량 부족
        try {
            file.transferTo(new File(savePath));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // description: 파일을 불러올 수 있는 경로 반환 //
        String url = fileUrl + saveFileName;
        return url;
        
    }
    
    // 파일 반환하기
    @Override
    public Resource getFile(String fileName) {
        
        Resource resource = null;
        
        // description: 파일 저장 경로에 있는 파일명에 해당하는 파일 불러오기 //
        try {
            resource = new UrlResource("file:" + filePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return resource;

    }
    
}
