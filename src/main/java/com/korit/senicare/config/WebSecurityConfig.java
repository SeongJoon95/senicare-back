package com.korit.senicare.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.korit.senicare.dto.response.ResponseCode;
import com.korit.senicare.dto.response.ResponseMessage;
import com.korit.senicare.filter.JwtAuthenticationFilter;
import com.korit.senicare.handler.OAuth2SuccessHandler;
import com.korit.senicare.service.implement.OAuth2UserServiceImplement;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

// Spring Web 보안 설정
@Configurable // Spring 설정 클래스
@Configuration // Spring 설정 클래스
@EnableWebSecurity // Spring Security를 활성화
@RequiredArgsConstructor // final로 선언된 필드를 자동으로 생성자를 통해 주입받고록 함. [JwtAuthenticationFilter가 주입된다.]
public class WebSecurityConfig {
    
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    // HTTP 요청이 서버로 들어올때 JWT 토큰을 검증하는데 사용 (JWT 기반 인증 필터)
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final OAuth2UserServiceImplement oAuth2Serviec;

    @Bean
    // Spring Security의 보안 설정을 직접 지정 -> HTTP 요청에 대한 보안 정책을 지정
    protected SecurityFilterChain configure(HttpSecurity security) throws Exception {

        security
            // basic 인증 방식 미사용 == 기존 HTTP 인증 방식을 사용하지 않겠다는 의미
            .httpBasic(HttpBasicConfigurer::disable)
            // session 미사용 (유지 X)
            .sessionManagement(sessionManagement -> sessionManagement
                // 서버가 상태를 지정하지 않은 STATELESS 정책을 사용/ 세션을 사용하지 않고 모든 요청은 JWT로 인증. 
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            // CSRF 보호기능 비활성화 -> JWT를 사용한 경우에는 CSRF 보호가 필요하지 않을수 있기 때문
            .csrf(CsrfConfigurer::disable)
            // CORS 정책 설정하는 메소드 호출
            .cors(cors -> cors.configurationSource(configurationSource()))
            // URL 패턴 및 HTTP 메서드에 따라 인증 및 인가 여부 지정 [특정 URL 인증 예외 처리]
            .authorizeHttpRequests(request -> request
                // '/api/v1/auth/**/' 이랑 file업로드에 관련된 '/file/*' 와 '/' 을 제외한 모든 요청은 인증을 필요로 함
                .requestMatchers("/api/v1/auth/**", "/oauth2/callback/*", "/file/*", "/").permitAll()
                .anyRequest().authenticated()
            )
            // 인증 및 인가 작업중 발생하는 예외 처리
            .exceptionHandling(exception -> exception
                .authenticationEntryPoint(new AuthenticationFailEntryPoint()))
            // oAuth2 로그인 적용
            .oauth2Login(oauth2 -> oauth2
                .redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
                .authorizationEndpoint(endpoint -> endpoint.baseUri("/api/v1/auth/sns-sign-in"))
                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2Serviec))
                .successHandler(oAuth2SuccessHandler)
            )
            // 필터 등록
            // jwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 배치하여 JWT 토큰을 검증하는 필터를 등록
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        
        return security.build();
    }
    
    @Bean
    protected CorsConfigurationSource configurationSource() {

        // CorsConfiguration : 모든 출처, 헤더, 메서드를 허용하는 CORS 정책을 정의.
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*"); // 모든 출처에 대해
        configuration.addAllowedHeader("*"); // 모든 Header에 대해
        configuration.addAllowedMethod("*"); // 모든 메서드에 대해
        
        // UrlBasedCorsConfigurationSource : 이 CORS 설정을 모든 경로에 적용 ["/**"]
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }

}

//! 인증실패에 관한 Class
class AuthenticationFailEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
            
        authException.printStackTrace();
        response.setContentType("application/json"); // json 형태의 data로 반환하기 위해
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write("{\"code\": \""+ ResponseCode.AUTHENTICATION_FAIL + "\", \"message\": \""+ ResponseMessage.AUTHENTICATION_FAIL +"\" }"); // JSON 형태로 작성
        // {"code":"AF", "message" : "Authentication fail"} 동일 ...
    }
    
}
