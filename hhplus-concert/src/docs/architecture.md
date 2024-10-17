## 패키지 구조

```txt
hhplusconcert/
├── src/main/java/com/hhplu/
│   └── hhplusconcert/
│       ├── application/                  
│       │   ├── usecase/                  
│       │   │   ├── GenerateQueueTokenUseCase.java   
│       │   │   ├── GetAvailableDatesUseCase.java    
│       │   │   ├── GetAvailableSeatsUseCase.java    
│       │   │   ├── ReserveSeatUseCase.java          
│       │   │   ├── ChargeBalanceUseCase.java        
│       │   │   ├── GetBalanceUseCase.java           
│       │   │   ├── PaymentProcessingUseCase.java  
│       │   │   ├── QueueProcessingScheduler.java   // 스케줄러의 역할을 확장하여 대기열 관리 및 만료 처리 등 모든 작업을 처리
│       │   ├── dto/                    // Application 레이어 내에 DTO 패키지
│       │   │   ├── request/            // Request DTO 패키지
│       │   │   │   ├── QueueRequest.java
│       │   │   │   ├── ConcertRequest.java
│       │   │   │   ├── ReservationRequest.java
│       │   │   │   ├── SeatRequest.java
│       │   │   │   └── PaymentRequest.java
│       │   │   ├── response/           // Response DTO 패키지
│       │   │   │   ├── QueueResponse.java
│       │   │   │   ├── ConcertResponse.java
│       │   │   │   ├── ReservationResponse.java
│       │   │   │   ├── SeatResponse.java
│       │   │   │   └── PaymentResponse.java
│       ├── domain/                       
│       │   ├── user/                     
│       │   │   └── User.java             
│       │   ├── concert/                  
│       │   │   └── Concert.java          
│       │   ├── concertschedule/          
│       │   │   └── ConcertSchedule.java  
│       │   ├── seat/                     
│       │   │   └── Seat.java             
│       │   ├── reservation/              
│       │   │   └── Reservation.java      
│       │   ├── queue/                    
│       │   │   └── Queue.java            
│       │   └── payment/                  
│       │       └── Payment.java          
│       ├── infrastructure/               
│       │   └── repository/               
│       │       ├── UserRepository.java   
│       │       ├── ConcertRepository.java   
│       │       ├── ConcertScheduleRepository.java   
│       │       ├── SeatRepository.java   
│       │       ├── ReservationRepository.java
│       │       ├── QueueRepository.java   
│       │       └── PaymentRepository.java 
│       └── interfaces/                   
│           └── api/                      
│               ├── user/              
│               │   ├── UserController.java  
│               ├── concert/              
│               │   ├── ConcertController.java  
│               ├── concertschedule/      
│               │   ├── ConcertScheduleController.java  
│               ├── seat/                
│               │   ├── SeatController.java   
│               ├── reservation/          
│               │   ├── ReservationController.java  
│               ├── queue/                
│               │   ├── QueueController.java     
│               ├── payment/              
│               │   ├── PaymentController.java  
├── resources/                            
│   └── application.yml                   
└── test/

```

## 패키지 구조 설명

1. **application**:
    - API 요청을 처리하기 위한 비즈니스 로직을 담당하는 application 레이어입니다. 각 API 요청에 대한 로직을 담당하는 클래스를 **UseCase**로 분리하여 넣었습니다. 예를 들어 `GenerateQueueTokenUseCase.java`는 유저 대기열 토큰 발급에 대한 로직을 처리합니다.
2. **domain**:
    - 시스템의 핵심 비즈니스 로직을 포함하는 도메인 엔티티들을 정의하는 패키지입니다. 예를 들어, **concert** 패키지에는 콘서트와 관련된 엔티티가 포함되어 콘서트 정보를 관리합니다.
3. **infrastructure**:
    - DB 연동 및 외부 API 호출 같은 기술적 세부 사항을 처리하는 레이어입니다. 예를 들어 **repository** 클래스들이 여기에 위치하여 데이터베이스와 상호작용을 처리합니다.
4. **interfaces**:
    - 외부와의 통신을 처리하는 interfaces 레이어입니다. API 요청 및 응답을 처리하는 **Controller**와 **DTO**(Data Transfer Object)들이 이 레이어에 포함되어 있습니다.
    - 각 기능에 맞는 API는 **balance**, **payment**, **queue**, **reservation**, **concert**로 나뉘어 관리되며, 각각의 DTO가 포함되어 있습니다.
5. **resources**:
    - 애플리케이션 설정 파일들이 포함된 디렉토리입니다. 여기에는 데이터베이스 설정, 서버 포트 설정 등 애플리케이션의 실행 환경 설정이 담긴 **application.yml** 파일이 있습니다.
6. **test**:
    - 애플리케이션의 테스트 코드가 작성되는 디렉토리입니다. 모든 유닛 테스트, 통합 테스트가 이 디렉토리 안에서 작성됩니다.

이 패키지 구조는 각 API 요청을 처리하는 UseCase 클래스를 명시적으로 구분하여 유지보수성과 확장성을 높였습니다.

## 기술스택
- **JDK 17**: 안정적인 장기 지원 버전(LTS)으로, 최신 기능을 제공하면서도 안정성을 보장하는 자바 개발 키트.
- **Spring Boot**: 빠르고 간편한 웹 애플리케이션 개발 환경을 제공하는 프레임워크.
- **Spring Data JPA**: 데이터베이스와의 상호작용을 추상화하고 간편한 ORM(Object-Relational Mapping)을 지원하는 라이브러리. 복잡한 SQL 없이도 자바 객체와 데이터베이스 간의 매핑을 쉽게 처리.
- **MySQL**: 오픈 소스 관계형 데이터베이스 관리 시스템(RDBMS)으로, 높은 성능과 안정성을 제공하며, 다양한 규모의 애플리케이션에서 많이 사용.
- **JUnit**: 자바 애플리케이션을 위한 표준적인 테스트 프레임워크로, 단위 테스트 및 통합 테스트 작성을 지원. 자동화된 테스트 실행을 통해 코드 품질을 보장할 수 있다.
- **AssertJ**: JUnit과 함께 사용되는 테스트 프레임워크로, 가독성 높은 테스트 작성과 다양한 어설션 메서드를 제공하여 테스트 코드를 간결하고 명확하게 작성할 수 있다.
