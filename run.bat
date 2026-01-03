@echo off
echo ================================
echo KAMCO 이력조회 시스템 실행
echo ================================

echo.
echo [1/3] 환경 변수 설정 중...
set KAMCO_SERVICE_KEY=your_service_key_here

echo 환경 변수가 설정되었습니다.
echo KAMCO_SERVICE_KEY=%KAMCO_SERVICE_KEY%
echo.

echo [2/3] 백엔드 서버 시작 중...
echo 백엔드 서버가 시작됩니다. 브라우저에서 다음 주소로 접속하세요:
echo - 백엔드 API: http://localhost:8001
echo - Swagger UI: http://localhost:8001/swagger-ui.html
echo - H2 Console: http://localhost:8001/h2-console
echo.

echo [3/3] 프론트엔드는 별도 터미널에서 실행하세요:
echo cd side-proj-frontend
echo npm install
echo npm run dev
echo - 프론트엔드: http://localhost:5173
echo.

cd side-proj_backend
call gradlew bootRun