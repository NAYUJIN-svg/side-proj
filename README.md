# KAMCO 공공데이터 연동 프로젝트

Spring Boot + MyBatis 기반 교육용 프로젝트

## 📁 프로젝트 구조

```
src/main/java/com/nyg/sideproj/
├── config/              # 설정 클래스
│   ├── SwaggerConfig.java      # API 문서 설정
│   └── WebClientConfig.java    # HTTP 클라이언트 설정
├── controller/          # REST API 컨트롤러
│   ├── KamcoController.java    # View 컨트롤러
│   └── KamcoRestController.java # REST API 컨트롤러
├── service/             # 비즈니스 로직
│   └── KamcoService.java       # KAMCO 서비스
├── mapper/              # MyBatis Mapper
│   └── KamcoMapper.java        # DB 쿼리 인터페이스
├── entity/              # DB Entity
│   └── KamcoItem.java          # KAMCO 엔티티
├── dto/                 # Data Transfer Object
│   ├── KamcoRequest.java       # 요청 DTO
│   ├── KamcoItemResponse.java  # 응답 아이템 DTO
│   └── KamcoResponse.java      # 응답 래퍼 DTO
└── SideProjApplication.java    # 메인 애플리케이션
```

## 🗄️ 데이터베이스

### 테이블 구조
- **kamco_by_mnmt_no**: 물건관리번호 기준 (UNIQUE: CLTR_MNMT_NO)
- **kamco_by_cltr_nm**: 물건명 기준 (UNIQUE: CLTR_NM)

### UPSERT 패턴
```sql
INSERT INTO table (columns...) VALUES (values...)
ON DUPLICATE KEY UPDATE column=VALUES(column)
```

## 🔑 환경 설정

### 서비스 키 설정 (필수)
```bash
# Windows
setx KAMCO_SERVICE_KEY "your-service-key"

# Linux/Mac
export KAMCO_SERVICE_KEY="your-service-key"
```

### 데이터베이스 설정
`application.properties` 파일에서 DB 연결 정보 수정
```properties
spring.datasource.url=jdbc:mariadb://localhost:3306/testdb
spring.datasource.username=root
spring.datasource.password=1234
```

## 🚀 API 엔드포인트

### 1. /api/kamco/mnmt
- **기능**: 물건관리번호 기준 중복 제거 10개 조회
- **중복 기준**: CLTR_MNMT_NO (물건관리번호)
- **응답**: XML (10개 아이템)

### 2. /api/kamco/cltr
- **기능**: 물건명 기준 중복 제거 10개 조회
- **중복 기준**: CLTR_NM (물건명)
- **응답**: XML (10개 아이템)

## 📚 학습 포인트

### 1. Spring Boot 기본 구조
- **Controller**: HTTP 요청 처리
- **Service**: 비즈니스 로직
- **Mapper**: 데이터베이스 접근
- **Entity**: DB 엔티티
- **DTO**: 데이터 전송 객체

### 2. MyBatis 어노테이션 기반 쿼리
```java
@Insert("INSERT INTO table ...")
void insert(KamcoItem item);
```

### 3. WebClient를 이용한 외부 API 호출
```java
webClientBuilder.build()
    .get()
    .uri(url)
    .retrieve()
    .bodyToMono(String.class)
    .block();
```

### 4. XML 파싱 (DOM Parser)
```java
DocumentBuilder builder = factory.newDocumentBuilder();
Document doc = builder.parse(inputStream);
NodeList items = doc.getElementsByTagName("item");
```

### 5. 트랜잭션 관리
```java
@Transactional
public KamcoResponse getMnmtList(int pageNo, int numOfRows) {
    // 트랜잭션 내에서 실행
}
```

### 6. 환경변수 사용
```java
String key = System.getenv("KAMCO_SERVICE_KEY");
```

## 🔧 실행 방법

1. 환경변수 설정
2. 데이터베이스 생성 및 테이블 생성
3. application.properties 수정
4. 프로젝트 실행
```bash
./gradlew bootRun
```
5. Swagger UI 접속: http://localhost:8001/swagger-ui.html

## 📝 주요 특징

- ✅ **단순한 구조**: Entity와 DTO 명확히 분리
- ✅ **명확한 네이밍**: 메서드와 변수명이 기능을 명확히 표현
- ✅ **주석 완비**: 모든 클래스와 메서드에 JavaDoc 주석
- ✅ **트랜잭션**: DB 무결성 보장
- ✅ **검증 로직**: 파라미터 및 데이터 검증
- ✅ **재사용성**: 공통 로직을 메서드로 분리
- ✅ **교육용**: 초보자도 이해하기 쉬운 코드 구조
