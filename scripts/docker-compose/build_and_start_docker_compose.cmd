REM Building sportscentre image and push it to docker hub

cd ../../backoffice/ConfigurationBOApi

REM Build with Maven (skip tests)
call mvn clean package -DskipTests

REM Build image
call docker build -t pablo7molina/configurationboapi .

REM Push image
call docker push pablo7molina/configurationboapi

cd ../../backoffice/BookingsService

REM Build with Maven (skip tests)
call mvn clean package -DskipTests

REM Build image
call docker build -t pablo7molina/bookingsservice .

REM Push image
call docker push pablo7molina/bookingsservice

REM Start Docker compose
docker-compose up -d