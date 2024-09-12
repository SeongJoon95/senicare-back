package com.korit.senicare.entity;

import com.korit.senicare.dto.request.auth.SignUpRequestDto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="nurses")
@Table(name="nurses")
public class NurseEntity {
    
    @Id
    private String userId;
    private String password;
    private String name;
    private String telNumber;
    private String joinPath;
    private String snsId;

    // 매개변수로 SignUpRequestDto 클래스를 dto로 받아옴
    public NurseEntity(SignUpRequestDto dto) {
        
        // 클라이언트로부터 받아온 값들(SignUpRequestDto)을 각각 필드에 맞게끔 저장
        this.userId = dto.getUserId();
        this.password = dto.getPassword();
        this.name = dto.getName();
        this.telNumber = dto.getTelNumber();
        this.joinPath = dto.getJoinPath();
        this.snsId = dto.getSnsId();
    }
}
