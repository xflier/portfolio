# PostgresQL Database in docker container

The following steps are all under the project folder

1. set up environment variables
2. create .env file with the following contents
UID=<uid>
GID=<gid>
POSTGRES_USER=user
POSTGRES_PASSWORD=1234


3. create a network docker_net

docker network create docker_net

4. create a directory to store db data, which will be mounted to the container
5. use the <uid> to create the directory

mkdir .data 

6. start the database - postgreSQL
docker compose up -d
