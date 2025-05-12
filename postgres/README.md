# PostgresQL Database in docker container

The following steps are all under the project folder

1. create a network docker_net if it is not created yet

docker network create docker_net

4. create a directory to store db data, which will be mounted to the container
5. use the <uid> to create the directory

mkdir .data 

6. start the database - postgreSQL
docker compose up -d
