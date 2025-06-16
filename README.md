# 🎟️ 콘서트 예약 시스템

트래픽이 몰리는 상황에서도 안정적인 예약 처리를 구현하기 위해 설계된 백엔드 중심의 콘서트 예매 시스템입니다.  
실무 환경을 가정한 교육 프로젝트로, 예약 대기열부터 좌석 예약, 결제 처리까지의 전체 흐름을 도메인 기반으로 나누어 구현했습니다.  
단순한 기능 구현을 넘어서 트랜잭션 관리, 동시성 제어, 예외 처리 등 실무 수준의 복잡도를 고려한 설계에 집중했습니다.

---

## 📌 프로젝트 목표

- 실시간 트래픽 상황에서도 안정적인 좌석 예약 로직 구현
- 명확한 도메인 분리와 클린 아키텍처 기반의 구조 설계
- 예외 상황, 만료 처리 등 비정상 흐름에 대한 명확한 대응
- 테스트 코드 기반의 신뢰성 있는 기능 구현

---

## 🧱 프로젝트 진행 단계

### ✅ STEP 05

- 시나리오 및 요구사항 분석
- 시퀀스 다이어그램, 플로우 차트 작성
- 마일스톤 수립 및 프로젝트 구조 계획

🔗 관련 자료  
- [시퀀스 다이어그램](https://github.com/JEONGBEOMKO/hhplus-concert/blob/main/hhplus-concert/src/docs/sequence_diagram.md)  
- [마일스톤 (GitHub Projects)](https://github.com/users/JEONGBEOMKO/projects/12)

---

### ✅ STEP 06

- ERD 설계 및 패키지 구조 정리
- API 명세 정의 및 Swagger 문서 작성
- 기본 서버 Configuration 구현

🔗 관련 자료  
- [API 명세](https://github.com/JEONGBEOMKO/hhplus-concert/blob/main/hhplus-concert/src/docs/api_specs.md)  
- [Swagger API 문서](https://github.com/JEONGBEOMKO/hhplus-concert/tree/main/hhplus-concert/src/docs/swagger)  
- [패키지 구조 및 아키텍처 설계](https://github.com/JEONGBEOMKO/hhplus-concert/blob/main/hhplus-concert/src/docs/architecture.md)

📷 Swagger 미리보기:

<img width="1121" alt="api_specs2" src="https://github.com/user-attachments/assets/2793aa59-6425-4f2e-8b84-0e1751826f40">
<img width="1144" alt="api_specs3" src="https://github.com/user-attachments/assets/e557148e-9d72-4d16-ab75-2fbeded10862">
<img width="1134" alt="api_specs4" src="https://github.com/user-attachments/assets/6e7b912b-28a9-48c8-a362-005083fdfd14">
<img width="1117" alt="api_specs5" src="https://github.com/user-attachments/assets/a051c1b8-eb5f-4376-ba99-17a084cf7981">

---

## ⚙️ 주요 기능

- 콘서트/스케줄 조회
- 사용자 대기열(Queue) 발급 및 진입 처리
- 좌석 예약 및 만료 처리
- 사용자 잔액 충전 및 결제
- 예외 상황 관리 및 응답 흐름 통제

---

## 🔍 고민한 지점

- 동시성 환경에서 예약 충돌을 어떻게 방지할 것인가?
- 좌석 상태와 예약 상태의 일관성을 어떻게 유지할 것인가?
- 도메인 간 책임을 분리하면서도 유지보수하기 쉬운 구조는?
- 예외 상황에서도 흐름을 자연스럽게 유지할 수 있는 설계 방식은?

---

## 🛠️ 기술 스택

- Java 17
- Spring Boot 3
- Spring Data JPA
- H2 (개발 DB), MySQL
- Redis (Queue 흐름 제어)
- Swagger (API 명세)
- JUnit5, AssertJ (테스트)

---

## 🧠 핵심 경험

- 예외 상황 중심의 흐름 설계
- 구조적 설계와 상태 전이 기반의 예약 처리
- 명확한 책임 분리를 위한 도메인 설계
- 테스트 코드 기반의 신뢰성 확보
