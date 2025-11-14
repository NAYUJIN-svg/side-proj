thymleaf 구현 
==========================================================================================================================================================

기획서 작성
주제 :  한국자산관리 공사 API를 활용한 입찰 정보공개 사이트 구축
전체적인 구조 : Spring boot + mybatis 
사용 툴 : intellj(Spring boot)
화면 구성 : thymleaf로 구현
화면 내용 : 물건명 테이블, 물건관리번호 테이블 
상세 구조 :  KamcoController,KamcoRestController —-KamcoRequest(dto) — KamcoService —KamcoResponse(dto) — KamcoMapper —-  DB
핵심 기능 : mybatis , mariaDB
검증 기능 : AOP기능 LoggingAspect
값의 흐름: 1. REST 요청(XML 응답)일 때 흐름KamcoRestController → KamcoService → KamcoMapper → DB → KamcoService → KamcoResponse → KamcoRestController(XML 반환)
흐름 상세
1. 클라이언트가 REST API 호출

예: /api/kamco/mnmt or /api/kamco/cltr
2. KamcoRestController가 KamcoRequest DTO를 받음
3. Service 호출
    ◦ 테이블 생성/삭제
    ◦ Mapper 호출
4. Mapper가 DB에서 물건명 / 물건관리번호 조회
5. DB 결과 → Mapper → Service로 전달
6. Service에서 DB 데이터를 KamcoResponse DTO로 변환
7. KamcoRestController가 Response DTO를 XML로 변환해 클라이언트에 반환
2. Thymeleaf 요청(HTML 화면 출력)일 때 흐름KamcoController → KamcoService → KamcoMapper → DB → KamcoService → Model에 담음 → Thymeleaf 템플릿 렌더링
흐름 상세
1. 사용자가 웹 브라우저에서 페이지 요청

예: /kamco/mnmt or /kamco/cltr
2. KamcoController가 KamcoRequest DTO를 받음
3. Service 호출
    ◦ 필요 시 테이블 작업
    ◦ Mapper 호출
4. Mapper가 DB에서 물건명 / 물건관리번호 조회
5. DB 결과 → Mapper → Service로 전달
6. Service에서 조회 데이터를 KamcoResponse DTO or List로 생성
7. Controller가 Model에 데이터를 넣음
8. Thymeleaf 페이지에서 데이터를 HTML 형태로 렌더링하여 화면 출력

필수 키워드
1. 개요(Overview)

무엇을 만들 것인지 한 줄 정의.
2. 목적(Purpose)

왜 만드는지, 해결하려는 문제.
3. 배경(Background)

현재 상황, 필요성, 시장/업무 맥락.
4. 목표(Goal)

정량/정성 목표, 성공 기준.
5. 기능 정의(Features / Requirements)

필수 기능, 선택 기능, 세부 요구사항.
6. 사용자(User)

타겟 사용자, 사용자 시나리오.
7. 흐름도(Flow / UX Flow)

화면 흐름, 기능 흐름.
8. 화면 기획(UI/UX)

화면 구조, 와이어프레임, 페이지 구성.
9. 데이터/DB 정의(Data Structure)

테이블, 필드, 관계.
10. 기술 스택(Tech Stack)

백엔드, 프론트엔드, DB, 배포 환경.
11. 일정(Schedule)

개발 일정, 마일스톤.
12. 리스크(Risk) & 고려사항
