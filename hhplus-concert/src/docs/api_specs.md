# API 명세

### 1. 유저 대기열 토큰 발급 API
   - Endpoint: POST /queue/token
   - HTTP Method: POST
   - 설명: 유저가 대기열에 진입하여 토큰을 발급받습니다.

#### Request 
- Headers:
  - Authorization: Bearer <JWT Token>

-  Body:
 ```
 {
  "user_id": "12345"
}
```
#### Response
- 200 OK
```
{
  "token": "abc123xyz",
  "queue_order": 5,
  "time_remaining": 300
}
```
- 400 Bad Request:
```
{
  "error": "유효하지 않은 사용자 ID"
}
```

---
### 2. 예약 가능 날짜 조회 API

- Endpoint: GET /reservations/dates
- HTTP Method: GET
- 설명: 예약 가능한 날짜 목록을 조회합니다.

#### Request
- Headers:
    - Authorization: Bearer <JWT Token>
- Query Parameters:
    - start_date: YYYY-MM-DD (시작 날짜)
    - end_date: YYYY-MM-DD (종료 날짜)

#### Response:
- 200 OK:
```
{
  "available_dates": ["2024-10-10", "2024-10-11"]
}
```
- 400 Bad Request:
```
{
  "error": "잘못된 날짜 형식"
}
```

---

### 3. 예약 가능 좌석 조회 API

- Endpoint: GET /reservations/seats
- HTTP Method: GET
- 설명: 특정 날짜에 예약 가능한 좌석 목록을 반환합니다.

#### Request
- Headers:
  - Authorization: Bearer <JWT Token>
- Query Parameters:
  - date: YYYY-MM-DD (예약 날짜)

#### Response:
- 200 OK:
```
{
  "available_seats": ["A1", "B2", "C3"]
}
```
- 400 Bad Request:
```
{
  "error": "잘못된 날짜 형식"
}
```

---

### 4. 좌석 예약 요청 API
- Endpoint: POST /reservations
- HTTP Method: POST
- 설명: 유저가 특정 날짜에 좌석을 예약합니다.

#### Request
- Headers:
  - Authorization: Bearer <JWT Token>
- Body
```
{
  "user_id": "12345",
  "seat_id": "A1",
  "date": "2024-10-10"
}
```

#### Response:
- 201 Created:
```
{
  "reservation_id": "res123",
  "seat_id": "A1",
  "date": "2024-10-10",
  "status": "pending"
}
```
- 409 Conflict:
```
{
  "error": "이미 예약된 좌석"
}
```

### 5. 잔액 충전 / 조회 API
- Endpoint: GET /balance
- HTTP Method: GET
- 설명: 유저의 현재 잔액을 조회합니다.

#### Request
- Headers:
  - Authorization: Bearer <JWT Token>

#### Response:
- 200 OK:
```
{
  "balance": 100.0
}
```
- 400 Bad Request:
```
{
  "error": "잘못된 요청"
}
```