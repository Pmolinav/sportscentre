REM End docker-compose
docker-compose down

REM Remove unused images that do not have name and could have been created in the process
docker image prune -f --filter "dangling=true"