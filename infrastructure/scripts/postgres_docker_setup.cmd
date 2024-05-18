REM DEPRECATED
REM Create spring network to connect our containers.
docker network create spring

REM Create container for postgres database.
docker run --name postgres -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=mysecretpassword -e POSTGRES_DB=sportscentre --network=spring -v data-postgres:/var/lib/postgresql/data -p 5432:5432 --restart=no -d postgres:15-alpine
