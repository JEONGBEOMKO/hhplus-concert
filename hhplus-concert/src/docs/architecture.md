## 패키지 구조

```txt
hhplusconcert/
├── src/main/java/com/hhplu/
│   └── hhplusconcert/
│       ├── application/                  
│       │   └── facade/                   
│       │       ├── BalanceFacade.java    
│       │       ├── PaymentFacade.java    
│       │       ├── QueueFacade.java      
│       │       └── ReservationFacade.java
│       ├── domain/                       
│       │   ├── balance/                  
│       │   ├── payment/                 
│       │   ├── queue/                   
│       │   └── reservation/             
│       ├── infrastructure/              
│       └── interfaces/                  
│           └── api/                      
│               ├── balance/              
│               │   ├── BalanceController.java 
│               │   └── dto/              
│               │       ├── Balance.java  
│               │       └── ChargeRequest.java 
│               ├── payment/              
│               │   ├── PaymentController.java 
│               │   └── dto/              
│               │       └── Payment.java  
│               ├── queue/                
│               │   ├── QueueController.java   
│               │   └── dto/              
│               │       └── Queue.java    
│               └── reservation/          
│                   ├── ReservationController.java 
│                   └── dto/              
│                       ├── Reservation.java    
│                       └── AvailableSeats.java 
├── resources/
│   └── application.yml  
└── test/                
```

## 패키지 설명
1. application/ 계층 (퍼사드 계층)
   - BalanceFacade.java, PaymentFacade.java, QueueFacade.java, ReservationFacade.java: 각 기능(잔액 관리, 결제, 대기열, 예약)과 관련된 비즈니스 로직을 처리하는 계층입니다. 이 레이어에서는 유스케이스나 주요 기능들이 구현됩니다.
   
2. domain/ 계층 (도메인 레이어)
   - balance/, payment/, queue/, reservation/: 도메인 레이어는 비즈니스 로직의 핵심인 도메인 모델을 정의합니다. 아직 도메인 엔티티와 로직은 작성되지 않았으며, 이 계층에 추가될 예정입니다.

3. infrastructure/ 계층 (인프라스트럭처 레이어)
   - DB 연동이나 외부 API와의 통신을 처리하는 레이어로, 리포지토리나 외부 통합과 관련된 클래스들이 작성될 예정입니다.

4. interfaces/ 계층 (인터페이스 레이어)
   - balance/, payment/, queue/, reservation/: 각 기능별로 API 컨트롤러와 DTO가 포함된 패키지입니다.
     - BalanceController.java: 잔액 조회 및 충전 API 처리
     - PaymentController.java: 결제 API 처리
     - QueueController.java: 대기열 및 토큰 발급 API 처리
     - ReservationController.java: 예약 관련 API 처리
     - 각 패키지의 dto/ 디렉토리에는 각 API에서 사용되는 요청(Request)과 응답(Response) 데이터를 처리하는 DTO가 포함됩니다.

5. resources/: 환경 설정 파일(application.yml)을 담고 있는 디렉토리입니다. 서버 포트, DB 연결 정보, 외부 API 연동 정보 등이 설정됩니다.

6. test/: 테스트 코드를 작성하는 디렉토리입니다. 각 API와 비즈니스 로직에 대한 단위 테스트 및 통합 테스트를 여기서 작성합니다.

## 기술스택
- **JDK 17**: 안정적인 장기 지원 버전(LTS)으로, 최신 기능을 제공하면서도 안정성을 보장하는 자바 개발 키트.
- **Spring Boot**: 빠르고 간편한 웹 애플리케이션 개발 환경을 제공하는 프레임워크.
- **Spring Data JPA**: 데이터베이스와의 상호작용을 추상화하고 간편한 ORM(Object-Relational Mapping)을 지원하는 라이브러리. 복잡한 SQL 없이도 자바 객체와 데이터베이스 간의 매핑을 쉽게 처리.
- **MySQL**: 오픈 소스 관계형 데이터베이스 관리 시스템(RDBMS)으로, 높은 성능과 안정성을 제공하며, 다양한 규모의 애플리케이션에서 많이 사용.
- **JUnit**: 자바 애플리케이션을 위한 표준적인 테스트 프레임워크로, 단위 테스트 및 통합 테스트 작성을 지원. 자동화된 테스트 실행을 통해 코드 품질을 보장할 수 있다.
- **AssertJ**: JUnit과 함께 사용되는 테스트 프레임워크로, 가독성 높은 테스트 작성과 다양한 어설션 메서드를 제공하여 테스트 코드를 간결하고 명확하게 작성할 수 있다.
