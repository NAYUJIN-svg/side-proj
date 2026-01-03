# KAMCO 공매물건 이력조회 시스템

한국자산관리공사(KAMCO) 공매물건 정보의 변동 이력을 실시간으로 추적하고 관리하는 시스템입니다.

## 🚀 주요 기능

### 백엔드 (Spring Boot + MyBatis)
- **이력조회 API**: 공매물건의 변동 이력을 실시간으로 추적
- **물건명 조회 API**: 기존 물건명 기반 조회 기능 유지
- **실시간 업데이트**: API를 통한 최신 데이터 동기화
- **전역 예외 처리**: 모든 오류를 백엔드에서 처리하여 안정성 확보
- **상세 로깅**: 오류 발생 지점 추적 및 디버깅 지원

### 프론트엔드 (React + Vite)
- **이력조회 화면**: 변동 이력을 테이블 형태로 표시
- **실시간 업데이트**: 버튼 클릭으로 최신 데이터 갱신
- **검색 기능**: 특정 물건관리번호로 이력 필터링
- **페이징 처리**: 대용량 데이터의 효율적 표시
- **반응형 디자인**: 모바일/태블릿 지원

## 📊 이력 관리 컬럼

다음 컬럼들의 변동사항을 추적합니다:
- `PBCT_CLTR_STAT_NM`: 공매물건상태명
- `MIN_BID_PRC`: 최저입찰가
- `FEE_RATE`: 수수료율
- `PBCT_BEGN_DTM`: 입찰시작일시
- `PBCT_CLS_DTM`: 입찰마감일시
- `USCBD_CNT`: 유찰횟수
- `IQRY_CNT`: 조회수

## 🛠 기술 스택

### 백엔드
- **Framework**: Spring Boot 3.x
- **ORM**: MyBatis
- **Database**: H2 (개발), MySQL (운영)
- **API Client**: WebClient
- **Documentation**: Swagger/OpenAPI 3
- **Logging**: SLF4J + Logback

### 프론트엔드
- **Framework**: React 19
- **Build Tool**: Vite
- **HTTP Client**: Axios
- **Styling**: CSS3 (반응형)
- **State Management**: React Hooks

## 📁 프로젝트 구조

```
side-proj/
├── side-proj_backend/          # Spring Boot 백엔드
│   ├── src/main/java/com/nyg/sideproj/
│   │   ├── controller/         # REST API 컨트롤러
│   │   ├── service/           # 비즈니스 로직
│   │   ├── mapper/            # MyBatis 매퍼
│   │   ├── entity/            # 데이터베이스 엔티티
│   │   ├── dto/               # 요청/응답 DTO
│   │   ├── exception/         # 전역 예외 처리
│   │   ├── config/            # 설정 클래스
│   │   └── util/              # 유틸리티
│   └── src/main/resources/
│       ├── application.properties
│       └── schema.sql
└── side-proj-frontend/         # React 프론트엔드
    ├── src/
    │   ├── components/        # React 컴포넌트
    │   ├── api/              # API 클라이언트
    │   ├── utils/            # 유틸리티 함수
    │   └── App.jsx
    └── package.json
```

## 🚀 실행 방법

### 1. 환경 변수 설정
```bash
# KAMCO API 서비스키 설정 (필수)
export KAMCO_SERVICE_KEY=your_service_key_here
```

### 2. 백엔드 실행
```bash
cd side-proj_backend
./gradlew bootRun
```
- 서버 주소: http://localhost:8080
- Swagger UI: http://localhost:8080/swagger-ui.html

### 3. 프론트엔드 실행
```bash
cd side-proj-frontend
npm install
npm run dev
```
- 개발 서버: http://localhost:5173

## 📡 API 엔드포인트

### 이력조회 API
- `POST /api/kamco/history/init` - 이력 데이터 초기화
- `GET /api/kamco/history` - 이력 데이터 조회 (페이징)
- `POST /api/kamco/history/update` - 실시간 이력 업데이트

### 물건명 API (기존 유지)
- `GET /api/kamco/cltr` - 물건명 조회
- `GET /api/kamco/backup/cltr` - 물건명 백업 데이터

## 🔧 주요 특징

### 예외 처리
- **GlobalExceptionHandler**: 모든 예외를 백엔드에서 처리
- **커스텀 예외**: KamcoApiException, DatabaseException
- **에러 응답 표준화**: 일관된 에러 메시지 형식

### 로깅
- **요청/응답 로깅**: 모든 API 호출 추적
- **에러 로깅**: 상세한 오류 정보 기록
- **성능 로깅**: API 응답 시간 측정

### 데이터 포맷팅
- **가격 포맷팅**: 천 단위 콤마 + 원화 표시
- **날짜 포맷팅**: YYYYMMDDHHMMSS → YYYY-MM-DD HH:MM:SS
- **상태 표시**: 색상 코딩된 상태 뱃지
- **반응형 테이블**: 모바일 환경 최적화

### 성능 최적화
- **페이징 처리**: 대용량 데이터 효율적 처리
- **중복 제거**: 동일한 물건의 중복 이력 방지
- **인덱스 최적화**: 데이터베이스 조회 성능 향상

## 🔒 보안

- **환경 변수**: 민감한 정보는 환경 변수로 관리
- **CORS 설정**: 허용된 도메인만 접근 가능
- **입력 검증**: 모든 요청 파라미터 유효성 검사

## 📈 모니터링

- **헬스 체크**: Spring Boot Actuator
- **메트릭 수집**: 애플리케이션 성능 지표
- **로그 분석**: 구조화된 로그 형식

## 🤝 기여 방법

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## 📄 라이선스

이 프로젝트는 개인 프로젝트로 제작되었습니다.

## 📞 문의

프로젝트 관련 문의사항이 있으시면 이슈를 등록해 주세요.