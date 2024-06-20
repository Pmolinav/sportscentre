@echo off

REM Change to the service directory
cd ../../backoffice/ConfigurationBOApi/

REM Build with Maven (skip tests)
call mvn clean package -DskipTests

REM Check if Maven build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven build failed for ConfigurationBOApi. Check the errors and try again.
    exit /b 1
)

REM Build Docker image
call docker build -t configurationboapi .

REM Check if Docker image build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: ConfigurationBOApi Docker image build failed.
    exit /b 1
)

REM Run the container
call docker run -d -p 8002:8002 --env-file .env --network=spring --name configurationboapi configurationboapi

REM Check if container execution was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to run ConfigurationBOApi Docker container.
    exit /b 1
)

echo ConfigurationBOApi has been built, Docker image has been created, and container has been started successfully!

REM Change to the service directory
cd ../../services/BookingsService/

REM Build with Maven (skip tests)
call mvn clean package -DskipTests

REM Check if Maven build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Maven build failed for BookingsService. Check the errors and try again.
    exit /b 1
)

REM Build Docker image
call docker build -t bookingsservice .

REM Check if Docker image build was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: BookingsService Docker image build failed.
    exit /b 1
)

REM Run the container
call docker run -d -p 8001:8001 --env-file .env --network=spring --name bookingsservice bookingsservice

REM Check if container execution was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to run BookingsService Docker container.
    exit /b 1
)

echo BookingsService has been built, Docker image has been created, and container has been started successfully!
