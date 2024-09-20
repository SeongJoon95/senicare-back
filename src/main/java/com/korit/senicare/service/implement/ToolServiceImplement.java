package com.korit.senicare.service.implement;

import java.util.*;

// import org.apache.catalina.startup.Tool;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.senicare.dto.request.tool.PatchToolRequestDto;
import com.korit.senicare.dto.request.tool.PostToolRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.tool.GetToolListResponseDto;
import com.korit.senicare.dto.response.tool.GetToolResponseDto;
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

    @Override
    public ResponseEntity<? super GetToolListResponseDto> getToolList() {
        
        // 빈 리스트 형태로 초기화 시켜줘야함
        List<ToolEntity> toolEntities = new ArrayList<>();

        try {
            toolEntities = toolRepository.findByOrderByToolNumberDesc();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }
        return GetToolListResponseDto.success(toolEntities);
    }

    @Override
    public ResponseEntity<? super GetToolResponseDto> getTool(Integer toolNumber) {
        
        ToolEntity toolEntity = null;

        try {
            
            // toolRepository에 있는 findByToolNumber를 사용하여 번호를 조회
            toolEntity = toolRepository.findByToolNumber(toolNumber);
            if(toolEntity == null) return ResponseDto.noExistTool(); // 

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }   
        
        return GetToolResponseDto.success(toolEntity);
    }

    @Override
    // 수정에 관한 연산처리 로직
    public ResponseEntity<ResponseDto> patchTool(Integer toolNumber, PatchToolRequestDto dto) {


        try {
            
            ToolEntity toolEntity = toolRepository.findByToolNumber(toolNumber);
            if(toolEntity == null) return ResponseDto.noExistTool();

            toolEntity.patch(dto);
            toolRepository.save(toolEntity);



        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();   
        }

        return ResponseDto.success();
    }

    @Override
    public ResponseEntity<ResponseDto> deleteTool(Integer toolNumber) {
        
        try {
            
            ToolEntity toolEntity = toolRepository.findByToolNumber(toolNumber);
            if(toolEntity == null) return ResponseDto.noExistTool();

            toolRepository.delete(toolEntity);

        } catch (Exception e) {
            
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }
    
}
