package com.korit.senicare.service.implement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.korit.senicare.common.util.AuthNumberCreator;
import com.korit.senicare.dto.request.auth.IdCheckRequestDto;
import com.korit.senicare.dto.request.auth.SignInRequestDto;
import com.korit.senicare.dto.request.auth.SignUpRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthCheckRequestDto;
import com.korit.senicare.dto.request.auth.TelAuthRequestDto;
import com.korit.senicare.dto.response.ResponseDto;
import com.korit.senicare.dto.response.auth.SignInResponseDto;
import com.korit.senicare.entity.NurseEntity;
import com.korit.senicare.entity.TelAuthNumberEntity;
import com.korit.senicare.provider.JwtProvider;
import com.korit.senicare.provider.SmsProvider;
import com.korit.senicare.repository.NurseRepository;
import com.korit.senicare.repository.TelAuthNumberRepository;
import com.korit.senicare.service.AuthService;

import lombok.RequiredArgsConstructor;

@Service // 스프링 빈으로 만드는 어노테이션 [/ == component]
@RequiredArgsConstructor
public class AuthServiceImplement implements AuthService{
    
    private final SmsProvider smsProvider;
    private final JwtProvider jwtProvider;

    private final NurseRepository nurseRepository;
    private final TelAuthNumberRepository telAuthNumberRepository;
// BCryptPasswordEncoder() 메서드를 통해 비밀번호 구현체를 만듦. // 비밀번호 암호화를 하기위한 변수 초기화
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    // 아이디 중복확인 작업 
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

    @Override
    // 인증번호 관련 메서드
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
        if (!isSendSuccess) return ResponseDto.messageSendFail();

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

    @Override
    // 회원가입 메서드
    //  ResponseEntity : HTTP 요청에 대한 응답을 제어하는 데 사용하는 클래스 // 응답 데이터와 HTTP 상태 코드를 직접 제어할 수 있는 클래스
    // ResponseDto 타입으로 반환처리를 해줌
    // 매개변수는 클라이언트로 부터 요청받은 값을 저장하고 있는 클래스(SignUpRequestDto)에서 받아옴
    public ResponseEntity<ResponseDto> signUp(SignUpRequestDto dto) {

        String userId = dto.getUserId(); // 클라이언트로부터 입력받은 userId를 문자열 타입 userId 변수에 값 저장
        String telNumber = dto.getTelNumber(); // 요청받은 전화번호를 telNumber이라는 변수에 저장
        String authNumber = dto.getAuthNumber(); // 요청받은 인증번호 'authNumber' 변수에 저장
        String password = dto.getPassword();

        try {
            
            boolean isExistedId = nurseRepository.existsByUserId(userId); // userId 가 있는지 조회해서 boolean타입 isExistedId에 저장
            if (isExistedId) return ResponseDto.duplicatedUserId(); // 아이디가 존재하면 중복으로 인한 에러 발생메소드를 리턴한다.

            boolean isExistedTelNumber = nurseRepository.existsByTelNumber(telNumber); // 변수로 받은 telNumber이라는 값이 존재하는지 비교
            if (isExistedTelNumber) return ResponseDto.duplicatedTelNumber(); // 전화번호가 존재할 경우 존재하는 전화번호에 대한 에러 메소드 전송

            boolean isMatched = telAuthNumberRepository.existsByTelNumberAndAuthNumber(telNumber, authNumber);
            if (!isMatched) return ResponseDto.telAuthFail(); // 인증번호가 일치하지 않을경우 처리하는 telAuthFail 메서드 

            String encodedPassword = passwordEncoder.encode(password);
            dto.setPassword(encodedPassword); // dto에 있는 password를 갈아 끼워주는거임

            // 
            NurseEntity nurseEntity = new NurseEntity(dto); 
            nurseRepository.save(nurseEntity); // 새로 받아온 값들을 넣어준뒤 저장해주는 역할

        } catch (Exception e) {
            e.printStackTrace(); // 예외처리
            return ResponseDto.databaseError(); // 이작업에 예외처리는 데이터베이스에 대한 에러말곤 없어서 이 메소드만 작성
        }

        return ResponseDto.success(); // 성공했을 경우 나타내는 메서드
    }

    // 로그인 관련 처리 메서드
    @Override
    public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
        
        // 아이디를 가져와서 담아준다.
        String userId = dto.getUserId();
        String password = dto.getPassword();
        String accessToken = null;

        try {
            
            NurseEntity nurseEntity = nurseRepository.findByUserId(userId);
            if (nurseEntity == null) return ResponseDto.signInFail(); // 존재하지 않는 아이디일 경우 나타내는 상태 메시지 

            String encodedPassword = nurseEntity.getPassword();
            boolean isMathced = passwordEncoder.matches(password, encodedPassword); // 비교 결과를 받을 변수 생성
            if (!isMathced) return ResponseDto.signInFail(); // 로그인 실패일 경우 두개가 매치되지 않았다는 것에 대한 실패반환메서드 

            accessToken = jwtProvider.create(userId);
            if (accessToken == null) return ResponseDto.tokenCreateFail(); 

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseDto.databaseError();
        }

        return SignInResponseDto.success(accessToken);
    }

}
