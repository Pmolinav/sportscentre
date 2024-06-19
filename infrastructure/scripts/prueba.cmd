@echo off

REM Define the paths of the services
set SERVICE_DIRS=../../backoffice/ConfigurationBOApi ../../services/OtherService

REM Iterate over each service path
for %%i in (%SERVICE_DIRS%) do (
    echo ========================
    echo Processing service at: %%i
    echo ========================

    REM Change to the service directory
    cd /D %%i

    REM Build with Maven (skip tests)
    call mvn clean package -DskipTests

    REM Check if Maven build was successful
    if %ERRORLEVEL% NEQ 0 (
        echo Error: Maven build failed for service %%i. Check the errors and try again.
        exit /b 1
    )

    REM Build Docker image
    call docker build -t configurationboapi-image .

    REM Check if Docker image build was successful
    if %ERRORLEVEL% NEQ 0 (
        echo Error: Docker image build failed for service %%i.
        exit /b 1
    )

    REM Run the container
    call docker run -d -p 8080:8080 --name configurationboapi configurationboapi-image

    REM Check if container execution was successful
    if %ERRORLEVEL% NEQ 0 (
        echo Error: Failed to run the Docker container for service %%i.
        exit /b 1
    )

    echo Service at path %%i has been built, Docker image has been created, and container has been started successfully!
)

echo All operations have been completed successfully for all services.
