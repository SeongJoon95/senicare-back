package com.korit.senicare.dto.request.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor // 파라미터가 없는 디폴트 생성자를 자동으로 생성
public class SignUpRequestDto {
    
    @NotBlank
    @Length(max=5) // 문자열의 길이를 나타내는 어노테이션
    private String name;
    @NotBlank
    @Length(max=20)
    private String userId;
    @NotBlank
    // regexp : 필드 값이 일치해야 하는 정규 표현식 패턴/ 이 속성은 필수로 설정해야 한다.
    @Pattern(regexp="^(?=.*[a-zA-Z])(?=.*[0-9]).{8,13}$") // ^: 문자열의 시작 // $: 문자열의 끝
    private String password;
    @NotBlank
    @Pattern(regexp="^[0-9]{11}$")
    private String telNumber; 
    @NotBlank
    private String authNumber;
    @NotBlank
    @Pattern(regexp="^(home|kakao|naver)$")
    private String joinPath;
    private String snsId;
}

