package com.korit.senicare.service.implement;

// import org.apache.catalina.startup.Tool;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.senicare.dto.request.tool.PostToolRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.entity.ToolEntity;
import com.korit.senicare.repository.ToolRepository;
import com.korit.senicare.service.ToolService;

import lombok.RequiredArgsConstructor;

// 용품관리에 관한 연산결과처리 클래스
@Service
@RequiredArgsConstructor
public class ToolServiceImplement implements ToolService {

    private final ToolRepository toolRepository;

    @Override
    // ResponseEntity : 상태코드, 메시지, 데이터 응답이 오는 것을 확인할수 있다.
    public ResponseEntity<ResponseDto> postTool(PostToolRequestDto dto) {
        
        try {
            
            ToolEntity toolEntity = new ToolEntity(dto);
            toolRepository.save(toolEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
    
}
