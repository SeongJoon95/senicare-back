package com.korit.senicare.service.implement;

import java.util.Map;
import java.util.HashMap;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.korit.senicare.common.object.CustomOAuth2User;
import com.korit.senicare.entity.NurseEntity;
import com.korit.senicare.provider.JwtProvider;
import com.korit.senicare.repository.NurseRepository;

import lombok.RequiredArgsConstructor;

// Spring Security의 OAuth2 인증 시스템에서 OAuth2 사용자 정보를 처리하는 서비스

@Service // 서비스 계층 빈 등록
@RequiredArgsConstructor // final 상수에 대한 생성자 
// DefaultOAuth2UserService 클래스 상속 받는다.
public class OAuth2UserServiceImplement extends DefaultOAuth2UserService {
    
    private final JwtProvider jwtProvider;

    private final NurseRepository nurseRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {

        OAuth2User oAuth2User = super.loadUser(request);
        // 클라이언트의 이름값을 가져오고 그 가져온 값들을 전부 소문자로 만든뒤 받음.
        String registration = request.getClientRegistration().getClientName().toLowerCase();
        
        try{
            System.out.println("=========================================================================");
            // ObjectMapper를 사용해 oAuth2User.getAttributes()로 받은 사용자 속성 정보를 JSON 문자열로 반환하여 출력
            // oAuth2User.getAttributes() : oAuth2USer는 OAuth2 인증이 성공한 후, 인증 서버에서 반환된 사용자 정보가 담긴 객체 / 
            // - 이 객체는 사용자의 이름, 이메일, 프로필 사진, ID등 다양한 사용자 속성 정보(attribute)들을 담고 있다.
            // - 사용자 속성 정보들을 Map<String, Object> 형식으로 반환
            // ObjectMapper는 Jackson 라이브러리에서 제공하는 클래스이며, Java 객체를 JSON
            // new ObjectMapper().writeValueAsString() : 
            
            System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
            System.out.println(oAuth2User.getName());
        }catch(Exception e) {
            e.printStackTrace();
        }
        
        String snsId = getSnsId(oAuth2User, registration);

        // oauth2 로 통해 받아온 정보를 확인하고
        NurseEntity nurseEntity = nurseRepository.findBySnsIdAndJoinPath(snsId, registration);
        
        CustomOAuth2User customOAuth2User = null;

        if (nurseEntity == null) {
            Map<String, Object> attributes = new HashMap<>();
            attributes.put("snsId", snsId);
            attributes.put("joinPath", registration);
            customOAuth2User = new CustomOAuth2User(snsId, attributes, false);
        } else {
            String userId = nurseEntity.getUserId();
            String token = jwtProvider.create(userId);

            Map<String, Object> attributes = new HashMap<>();
            attributes.put("accessToken", token);
            
            customOAuth2User = new CustomOAuth2User(userId, attributes, true);
            // d
        }

        return customOAuth2User;
    }

    private String getSnsId(OAuth2User oAuth2User, String registration) {
        String snsId = null;

        if (registration.equals("kakao")) {
            snsId = oAuth2User.getName();
        }
        if (registration.equals("naver")) {
            Map<String, String> response = (Map<String, String>) oAuth2User.getAttributes().get("response");
            snsId= response.get("id");
        }

        return snsId;
    }
    
}

/*

new ObjectMapper().writeValueAsString()
ObjectMapper는 Jackson 라이브러리에서 제공하는 클래스이며, Java 객체를 JSON 형식으로 변환하거나 JSON 문자열을 Java 객체로 변환할 때 사용됩니다.

new ObjectMapper():

Jackson의 ObjectMapper 객체를 생성합니다. 이 객체는 데이터 형식을 JSON으로 변환하는 데 사용됩니다.
writeValueAsString():

writeValueAsString() 메서드는 Java 객체를 JSON 형식의 문자열로 변환합니다.
여기서는 oAuth2User.getAttributes()로 받은 사용자 속성 정보(Map)를 JSON 형식의 문자열로 변환합니다.


 */