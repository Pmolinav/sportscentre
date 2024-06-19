@echo off

REM Change to the service directory
cd ../../backoffice/ConfigurationBOApi/

REM Stop container.
call docker stop configurationboapi

REM Check if Docker container stop was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Docker container stop failed.
    exit /b 1
)

REM Remove container.
call docker rm configurationboapi

REM Check if container remove was successful
if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to remove the Docker container.
    exit /b 1
)

REM Remove image.
call docker rmi configurationboapi-image

if %ERRORLEVEL% NEQ 0 (
    echo Error: Failed to remove Docker image.
    exit /b 1
)

echo The Docker container and the image were removed successfully!
