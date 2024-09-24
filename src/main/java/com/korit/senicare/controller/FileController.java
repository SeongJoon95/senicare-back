package com.korit.senicare.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import com.korit.senicare.service.FileService;

import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;



@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {
    
    private final FileService fileService;

    @PostMapping("/upload")
    public String upload(
        // MultipartFile: 무엇인가? 알아봐라
        @RequestParam("file") MultipartFile file
    ) {
        String url = fileService.upload(file);
        return url;
    }

    @GetMapping(value ="/{fileName}", produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE})
    public Resource getImageFile(
        @PathVariable("fileName") String fileName // PathVariable에서 읽어온 값을 String 타입 변수 fileName에 담아주는것,
    ) {
        Resource resource = fileService.getFile(fileName);
        return resource;
    }
    
}
