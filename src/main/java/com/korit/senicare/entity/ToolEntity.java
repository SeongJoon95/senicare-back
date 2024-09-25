package com.korit.senicare.entity;

import com.korit.senicare.dto.request.tool.PatchToolRequestDto;
import com.korit.senicare.dto.request.tool.PostToolRequestDto;

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
public class ToolEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // -> 데이터 베이스 PK의 AI와 동일
    private Integer toolNumber;
    private String name;
    private String purpose;
    private Integer count;

    public ToolEntity(PostToolRequestDto dto) {
        this.name = dto.getName();
        this.purpose = dto.getPurpose();
        this.count = dto.getCount();
    }
    
    public void patch(PatchToolRequestDto dto) {
        this.name = dto.getName();
        this.purpose = dto.getPurpose();
        this.count = dto.getCount();
    }

    public void decreaseCount(Integer usedCount){
        // 원래 있던 count에 usedCount를 빼고 다시 count에 집어넣는 방법
        this.count -= usedCount;
    }

}

