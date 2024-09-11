package com.korit.senicare.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.korit.senicare.common.util.AuthNumberCreator;
import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.entity.TelAuthNumberEntity;
import com.korit.senicare.provider.SmsProvider;
import com.korit.senicare.repository.NurseRepository;
import com.korit.senicare.repository.TelAuthNumberRepository;
import com.korit.senicare.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service // 스프링 빈으로 만드는 어노테이션 [/ == component]
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{
    
    private final SmsProvider smsProvider;

    private final NurseRepository nurseRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;

    @Override
    public ResponseEntity<ResponseDto> idCheck(IdCheckRequestDto dto) {
        
        String userId = dto.getUserId();

        try{
            boolean isExistedId = nurseRepository.existsByUserId(userId);
            // if(isExistedId) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseDto("DI","Duplicated user id"));
            if(isExistedId) return ResponseDto.duplicatedUserId();

        } catch(Exception e) {
            e.printStackTrace();
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DBE","Database error"));
            return ResponseDto.databaseError();

        }

        // return ResponseEntity.status(HttpStatus.OK).body(new ResponseDto("SU","Success."));
        return ResponseDto.success();
    }

    // 인증번호 관련 메서드
    @Override
    public ResponseEntity<ResponseDto> telAuth(TelAuthRequestDto dto) {
        
        String telNumber = dto.getTelNumber();

        try {
            
            // existsByTelNumber : 전화번호가 이미 등록되어 있는지 확인하는 메서드 
            // 중복된 경우 ResponseDto.duplicatedTelNumber(); 를 반환
            boolean isExistedTelNumber = nurseRepository.existsByTelNumber(telNumber);
            if (isExistedTelNumber) return ResponseDto.duplicatedTelNumber();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();   
        }
        
        // common.util 폴더 안의 AuthNumberCreator.number4() 클래스 메서드를 통해
        // 랜덤한 4자리 숫자를 String 타입으로 저장
        String authNumber = AuthNumberCreator.number4(); 

        // smsProvider.sendMessage : 인증번호를 SMS로 전송. 전송이 실패할 경우
        // ResponseDto.messageSendFail()를 반환
        boolean isSendSuccess = smsProvider.sendMessage(telNumber, authNumber);
        if (!isSendSuccess) return ResponseDto.messageDendFail();

        try {
            
            TelAuthNumberEntity telAuthNumberEntity = new TelAuthNumberEntity(telNumber,authNumber);
            telAuthNumberRepository.save(telAuthNumberEntity);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

    @Override
    // 인증번호 확인메서드
    // ResponseEntity<ResponseDto> : 메서드의 반환타입. 이 메서드는 HTTP 응답(ResponseEntity)로 래핑된 ResponseDto 객체를 반환
    // ResponseEntity : HTTP 상태 코드와 헤더, 본문을 포함한 응답을 나타냄.
    // ResponseDto : 응답 데이터를 담는 객체로, 응답 상태와 메시지를 포함할 수 있음.
    public ResponseEntity<ResponseDto> telAuthCheck(TelAuthCheckRequestDto dto) {
        
        String telNumber = dto.getTelNumber();
        String authNumber = dto.getAuthNumber();

        try {
            
            boolean isMatched = telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if(!isMatched) return ResponseDto.telAuthFail();


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return ResponseDto.success();
    }

}
