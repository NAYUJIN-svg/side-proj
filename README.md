기획서 — 한국자산관리공사(KAMCO) API를 활용한 입찰정보공개 사이트
1. 개요 (Overview)

한국자산관리공사 공개 API를 이용해 입찰·물건 정보를 수집·저장·조회하는 웹서비스 구축. REST/XML 및 HTML(Thymeleaf) 출력을 모두 제공한다.

2. 한 줄 정의

KAMCO API의 물건명·물건관리번호를 수집·보관하고 REST/XML·웹 화면으로 제공하는 Spring Boot + MyBatis 애플리케이션.

3. 목적 (Purpose)

공개된 입찰·자산 정보를 실시간/주기적으로 수집해 검색·조회 편의 제공

공공데이터 기반 투명한 정보 공개 및 분석 기반 마련

4. 배경 (Background)

KAMCO 공공데이터는 XML 응답으로 제공되어 파싱·보관 필요

단일 API 호출로는 실무 활용(검색, 상세보기, 통계) 한계 → DB에 저장해 재사용성 확보

5. 목표 (Goal)

정량 목표: 주요 테이블(물건명·물건관리번호) CRUD, REST API 6개 엔드포인트, Thymeleaf 4개 화면 구현

정성 목표: 안정적 스케줄 수집, 로깅·검증(AOP) 적용, 응답 XML 포맷 준수

성공 기준: API 응답·화면 렌더링이 모두 정상 동작하고, DB 저장 후 검색 응답 시간 < 300ms

6. 기능 정의 (Features / Requirements)
필수 기능

KAMCO API 호출(REST/XML) → 파싱 → DB 저장(물건명, 물건관리번호)

REST API (XML 반환) : /api/kamco/mnmt, /api/kamco/cltr 등

웹 UI(Thymeleaf) : 목록, 상세, 검색, 페이징

MyBatis 매퍼로 DB 연동 (mapper + mapper.xml)

AOP 기반 검증/로깅(LoggingAspect)

DB: MariaDB, 스키마 마이그레이션(DDL 스크립트 포함)

선택 기능

스케줄러(예: Spring @Scheduled)로 주기 수집

간단한 통계/대시보드(물건 수, 최근 업데이트 등)

엑셀/CSV 다운로드

세부 요구사항

XML 응답을 DTO(KamcoResponse)로 바인딩 후 DB 매핑

트랜잭션 처리: 테이블 생성/삭제/삽입은 서비스 레벨 트랜잭션 적용

입력값 검증: KamcoRequest 필드 유효성(AOP 또는 Spring Validator)

7. 사용자 (User) / 시나리오

대상 사용자: 일반 국민, 공공정보 분석가, 중개업자

시나리오 1: 사용자가 웹에서 물건명 검색 → 목록/페이징 → 상세보기

시나리오 2: 외부 시스템이 XML REST 엔드포인트 호출 → 최신 물건관리번호 목록 전달

8. 흐름도 (Flow / UX Flow)
REST(XML) 흐름

클라이언트 → /api/kamco/mnmt 호출 (GET/POST)

KamcoRestController가 KamcoRequest 수신

KamcoService 호출: 외부 KAMCO API 호출 / 파싱 / DB 작업(생성/삭제/삽입)

KamcoMapper → MariaDB 조회/삽입

DB 결과 → Service에서 KamcoResponse 생성

KamcoRestController가 KamcoResponse를 XML로 변환하여 반환

Thymeleaf(HTML) 흐름

브라우저 → /kamco/mnmt 요청

KamcoController가 KamcoRequest 수신

KamcoService 호출 → KamcoMapper 조회

Service가 조회 결과를 Model에 추가

Thymeleaf 템플릿이 Model로 렌더링 → HTML 응답

9. 화면 기획 (UI/UX)

페이지 목록

/kamco/mnmt : 물건명 목록(검색, 정렬, 페이징)

/kamco/mnmt/{id} : 물건명 상세

/kamco/cltr : 물건관리번호 목록

/ : 대시보드(수집 상태, 최근 업데이트)

와이어프레임(간단)

상단: 네비게이션(대시보드, 물건명, 물건관리번호)

왼쪽: 검색 패널(키워드, 날짜범위)

중앙: 결과 테이블(컬럼: 물건명/관리번호/위치/등록일/상태)

하단: 페이징 컨트롤

10. 데이터/DB 정의 (Data Structure)
테이블: kamco_item_name (물건명)
CREATE TABLE kamco_item_name (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  item_name VARCHAR(400) NOT NULL,
  kamco_item_id VARCHAR(100) UNIQUE, -- 공사 고유 아이디
  category VARCHAR(100),
  address VARCHAR(255),
  price DECIMAL(15,2),
  status VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

테이블: kamco_item_control (물건관리번호)
CREATE TABLE kamco_item_control (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  control_no VARCHAR(100) UNIQUE NOT NULL, -- 물건관리번호
  item_name_id BIGINT, -- FK -> kamco_item_name.id
  protocol_xml TEXT, -- 원본 XML(선택)
  source_url VARCHAR(512),
  collected_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (item_name_id) REFERENCES kamco_item_name(id)
);

DTO 예시

KamcoRequest { String startDate; String endDate; String query; int page; int size; }

KamcoResponse { List<ItemDto> items; Meta meta; }

11. 기술 스택 (Tech Stack)

백엔드: Java 17, Spring Boot (Web, Data, AOP, Validation)

DB: MariaDB

ORM/Mapper: MyBatis + mapper.xml

템플릿: Thymeleaf

빌드: Gradle 또는 Maven

로깅: SLF4J + Logback

XML: Jackson DataFormat XML 또는 JAXB (XML<->DTO 변환)

배포: Docker 컨테이너 (optional), 베어메탈 또는 클라우드(AWS/GCP) 가능

12. API 설계 (핵심 엔드포인트)

GET /api/kamco/mnmt — 물건명 목록(XML) (쿼리: startDate, endDate, page, size)

GET /api/kamco/mnmt/{id} — 물건명 상세(XML)

GET /kamco/mnmt — 물건명 목록(HTML)

GET /kamco/mnmt/{id}/view — 물건명 상세(HTML)

POST /admin/kamco/sync — 즉시 동기화(관리자용, 내부)

GET /api/kamco/cltr — 물건관리번호 목록(XML)

응답 포맷: XML 스키마는 KAMCO 원본 구조를 반영. Spring: @RestController + MappingJackson2XmlHttpMessageConverter 또는 @XmlRootElement DTO 사용.

13. MyBatis 매핑 구조

KamcoMapper 인터페이스: List<KamcoItem> findItems(Map params);, int insertItem(KamcoItem item); 등

resources/mapper/KamcoMapper.xml에 SQL 정의 및 resultMap 작성

14. 검증 및 AOP (LoggingAspect)

목적: 입력값 검증, 메서드 실행 시간 측정, 예외 로깅, 트랜잭션 모니터링

구현 포인트컷 예: @Pointcut("execution(* com.project.service..*(..))")

Advice: @Around — 요청/응답 로깅, 처리시간 기록

예외 처리: @AfterThrowing에서 예외 로그 및 알림(선택)

샘플 로그 항목: 메서드명, 파라미터(민감 데이터 마스킹), 처리시간, 예외 스택

15. 보안/운영 고려사항

API 키(공공데이터 서비스키) 환경변수로 관리 (application.yml에 노출 금지)

DB 커넥션 풀(ValidationQuery) 설정

입력 파라미터 검증(Injection 방지)

원본 XML 저장 시 개인정보 포함 여부 검토 및 암호화/마스킹 필요시 적용

16. 일정 (Schedule / 마일스톤) — 제안 (6주 기준)

Week 1: 요구정의, ERD 설계, API 명세, 환경 세팅

Week 2: DB DDL, MyBatis 기본 매퍼, DTO 설계

Week 3: 외부 KAMCO API 연동(파싱), REST 컨트롤러 구현

Week 4: Thymeleaf 화면(목록/상세), 페이징/검색 구현

Week 5: AOP(로깅/검증), 예외처리, 단위테스트/통합테스트

Week 6: 성능/부하 점검, 배포(도커), 문서화

17. 리스크 & 고려사항 (Risk)

KAMCO API 응답 포맷 변경 → 파싱 실패 가능성 (대응: 스키마 기반 검증, 버전 관리)

대량 데이터 수집 시 DB 부하 → 페이징·배치 처리 필요

개인정보 포함 데이터 저장 위험 → 수집 범위 제한 및 마스킹 필요

외부 API 호출 지연/에러 → 재시도 및 백오프 전략 필요

18. 추가 구현/확장 제안

캡차·로그인 적용(관리자 전용 동기화)

전체 텍스트 검색(Elasticsearch 연계)

알림(새 물건 등록 시 이메일/웹훅)

19. 산출물 (Deliverables)

소스 코드 (Spring Boot 프로젝트)

DB DDL 스크립트 및 샘플 데이터

API 명세서 (OpenAPI/Swagger)

운영 가이드(환경변수, 배포절차)

간단 사용자 매뉴얼(화면 설명)

20. 기술적 팁(빠른 참고)

XML ↔ DTO: com.fasterxml.jackson.dataformat:jackson-dataformat-xml 사용 권장

MyBatis 트랜잭션: Service 레이어에서 @Transactional 적용

Thymeleaf 페이징: Pageable 사용 또는 직접 페이징 로직 구현

AOP 로깅시 민감정보(개인 식별자) 필드는 마스킹 처리
