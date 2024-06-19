@echo off

REM Change to the service directory
cd ../../backoffice/ConfigurationBOApi/

REM Build with Maven (skip tests)
call mvn clean package -DskipTests

REM Check if Maven build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven build failed. Check the errors and try again.
    exit /b 1
)

REM Build Docker image
call docker build -t configurationboapi-image .

REM Check if Docker image build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Docker image build failed.
    exit /b 1
)

REM Run the container
call docker run -d -p 8002:8002 --env-file .env --name configurationboapi configurationboapi-image

REM Check if container execution was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to run the Docker container.
    exit /b 1
)

echo The service has been built, Docker image has been created, and container has been started successfully!
