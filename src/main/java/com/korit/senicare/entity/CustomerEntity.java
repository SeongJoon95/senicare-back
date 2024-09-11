package com.korit.senicare.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Entity(name="customers")
@Table(name="customers")
public class CustomerEntity {
    
    @Id
    // GeneratedValue : 엔티티의 기본 키 값을 자동으로 생성할 수 있게 하는 어노테이션
    // strategy=GenerationType.IDENTITY : IDENTITY 전략을 사용해 기본키를 생성 == 데이터베이스의 AI(auto-increment)와 동일
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer customNumber;
    private String profileImage;
    private String name;
    private String brith;
    private String charger;
    private String address;
    private String location;

}
