# 콘서트 예약 서비스
---
### **`STEP 05`**

- 시나리오 선정 및 프로젝트 Milestone 제출
- 시나리오 요구사항 별 분석 자료 제출
    
    > 시퀀스 다이어그램, 플로우 차트 등
    > 
- 자료들을 리드미에 작성 후 PR 링크 제출

### **`STEP 06`**

- ERD 설계 자료 제출
- API 명세 및 Mock API 작성
- 자료들을 리드미에 작성 후 PR링크 제출 ( 기본 패키지 구조, 서버 Configuration 등 )

---
### **`STEP 05 PR 링크`** 
- [시퀀스 다이어그램](https://github.com/JEONGBEOMKO/hhplus-concert/blob/main/hhplus-concert/src/docs/sequence-diagram.md)
- [마일스톤](https://github.com/users/JEONGBEOMKO/projects/12/views/1)

### **`STEP 06 PR 링크`**
- [API 명세](https://github.com/JEONGBEOMKO/hhplus-concert/blob/main/hhplus-concert/src/docs/api_specs.md)
- [API 명세(Swagger)](https://github.com/JEONGBEOMKO/hhplus-concert/tree/main/hhplus-concert/src/docs/swagger)

---
### **`패키지 구조`**

/src
  /main
    /java
      /com/example/project
        /interfaces
          /api
            /balance
              BalanceController.java
              dto
                Balance.java       // 잔액 조회 및 충전 처리
                ChargeRequest.java  // 잔액 충전 요청 DTO
            /payment
              PaymentController.java
              dto
                Payment.java        // 결제 처리 (Request/Response 통합)
            /queue
              QueueController.java
              dto
                Queue.java          // 토큰 발급 및 대기열 정보 DTO
            /reservation
              ReservationController.java
              dto
                Reservation.java    // 예약 정보 처리 (Request/Response 통합)
        /application
          (서비스 계층, UseCase 작성)
        /domain
          (도메인 계층, 엔티티 및 비즈니스 로직 작성)
        /infrastructure
          (DB, 외부 API 연동 및 리포지토리 작성)
  /test
    (테스트 코드 작성)

 이 시나리오 패키지 구조는 레이어드아키텍처와 클린 아키텍처를 준수하여 패키지 구조를 설계하였다.   
