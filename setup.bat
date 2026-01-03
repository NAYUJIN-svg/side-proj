@echo off
echo ================================
echo KAMCO 이력조회 시스템 설치 및 실행
echo ================================

echo.
echo [1/3] 프론트엔드 의존성 설치 중...
cd side-proj-frontend
call npm install

if %errorlevel% neq 0 (
    echo 오류: npm install 실패
    pause
    exit /b 1
)

echo.
echo [2/3] 백엔드 빌드 중...
cd ..\side-proj_backend
call gradlew build -x test

if %errorlevel% neq 0 (
    echo 오류: 백엔드 빌드 실패
    pause
    exit /b 1
)

echo.
echo [3/3] 설치 완료!
echo.
echo 실행 방법:
echo 1. 환경 변수 설정: set KAMCO_SERVICE_KEY=your_service_key
echo 2. 백엔드 실행: cd side-proj_backend && gradlew bootRun
echo 3. 프론트엔드 실행: cd side-proj-frontend && npm run dev
echo.
echo 백엔드: http://localhost:8080
echo 프론트엔드: http://localhost:5173
echo Swagger UI: http://localhost:8080/swagger-ui.html
echo.
pause