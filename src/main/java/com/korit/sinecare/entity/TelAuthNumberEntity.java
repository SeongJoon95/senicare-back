package com.korit.sinecare.entity;

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
@Entity(name="telAuthNumber")
@Table(name= "tel_auth_number")
public class TelAuthNumberEntity {

    @Id // pk를 지정하기 위한 어노테이션
    private String telNumber;
    private String authNumber;
    
}
