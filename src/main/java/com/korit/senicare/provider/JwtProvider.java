package com.korit.senicare.provider;

import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.security.Key;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// calss: JWT 생성 및 검증 기능 제공자
// - JWT 암호화 알고리즘 HS256
// - 비밀키 환경변수에 있는 jwt.secret
// - JWT 만료기간 10시간

@Component // Spring Bean으로 만들어 주는 어노테이션 // @Service도 있음
public class JwtProvider {
    
    @Value("${jwt.secret}")
    private String secetKey;

    // JWT 생성 메서드
    public String create(String userId) {

        // 만료시간 = 현재시간 + 10시간
        Date expiredDate = Date.from(Instant.now().plus(10,ChronoUnit.HOURS));

        String jwt = null;

        try {
            
            // JWT 암호화에 사용할 Key 생성
            Key key = Keys.hmacShaKeyFor(secetKey.getBytes(StandardCharsets.UTF_8));

            // JWT 생성
            jwt = Jwts.builder()
                .signWith(key, SignatureAlgorithm.HS256) // 서명과 관련된
                .setSubject(userId)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .compact(); // 압축하고 인코딩 하는 메서드
        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return jwt;
    }

    // 검증 메서드
    public String validate(String jwt) {

        String userId = null;

        try {

            // JWT 암호화에 사용할 Key 생성
            Key key = Keys.hmacShaKeyFor(secetKey.getBytes(StandardCharsets.UTF_8));
        
            // jwt 검증 및 payload의 subject 값 추출
            userId = Jwts.parserBuilder()
                    .setSigningKey(key) // 서명할때 썻던 key
                    .build() // 열수 있는 틀
                    .parseClaimsJws(jwt) // 파싱 작업 -> 
                    .getBody() // 페이로드 꺼내옴
                    .getSubject(); // 페이로드안에서 Subject 가져옴
        
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return userId;

    }

}
