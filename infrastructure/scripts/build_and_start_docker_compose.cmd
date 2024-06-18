REM Building sportscentre image and push it to docker hub
docker build -t pablo7molina/configurationboapi .
docker build -t pablo7molina/bookingsservice .

docker push pablo7molina/configurationboapi
docker push pablo7molina/bookingsservice