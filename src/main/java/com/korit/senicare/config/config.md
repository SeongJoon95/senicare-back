#### config 폴더
- 애플리케이션의 설정 파일들이 들어가는 곳.
- Spring Security 설정, 데이터베이스 설정, 또는 기타 애플리케이션 전반에 필요한 설정을 여기에 작성.

---
###### WebSecurityConfig Class
: Spring Boot 애플리케이션의 보안 설정을 관리하기 위한 설정으로, JWT 기반 인증을 처리하며 세션을 사용하지 않고, 특정 URL에 대해서는 인증을 요구하지 않으며, CORS 설정도 모든 요청에 대해 허용.