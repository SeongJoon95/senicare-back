package com.korit.sinecare.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// 서버 정상작동 확인용 [서버가 열려 있는지 확인하는 용도 ]
@RestController
@RequestMapping("/")
public class MainController {
    
    @GetMapping("")
    public String main(){
        return "Server on...";
    }

}
