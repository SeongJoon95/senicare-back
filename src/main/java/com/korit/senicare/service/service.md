##### service 폴더
- 비즈니스 로직을 구현하는 클래스들이 위치하는 폴더
- 데이터베이스와의 상호작용, 계산 및 복잡한 처리를 담당.

######  implement 폴더
- service폴더 내에서 인터페이스의 구현체를 위치시키는 서브 폴더.
- 인터페이스에 정의된 메서드를 실제로 구현한 클래스들을 포함

---
###### AuthServiceImplement Class | 실질적으로 클라이언트로부터 받은 요청을 연산하는 클래스
- 사용자 ID와 전화번호 인증 관련 비즈니스 로직처리
- ID중복 확인과 전화번호 중복 확인을 데이터베이스에서 수행한 후, 인증 번호를 생성하고 SMS로 전송하며, 인증 번호를 데이터베이스에 저장한다.
- SMS 전송 실패나 데이터베이스 오류가 발생할 경우 적절한 오류 응답을 반환.
1. idCheck메서드 : 아이디 중복확인
- 
2. telAuth : 인증번호 관련 메서드
3. telAuthCheck : 인증번호 확인 메서드
4. signUp : 회원가입 메서드
5. signIn : 로그인 관련 처리 메서드
