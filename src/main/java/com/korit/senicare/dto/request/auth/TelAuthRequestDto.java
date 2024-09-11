package com.korit.senicare.dto.request.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TelAuthRequestDto {
    
    @NotBlank // 반드시 값이 들어올수 있도록 하는 어노테이션
    @Pattern(regexp="^[0-9]{11}$")
    private String telNumber;
}
