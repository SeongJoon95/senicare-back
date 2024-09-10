package com.korit.sinecare.entity;

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
@Entity(name="tools")
@Table(name="tools")
public class ToolsEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // -> 데이터 베이스 PK의 AI와 동일
    private Integer toolNumber;
    private String name;
    private String purpose;
    private String count;
}
