PostgresQL Database in docker container

# The following steps are all under the project folder

# set up environment variables
# create .env file with the following contents
UID=<uid>
GID=<gid>
POSTGRES_USER=user
POSTGRES_PASSWORD=1234


# create a network docker_net

docker network create docker_net

# create a directory to store db data, which will be mounted to the container
# use the <uid> to create the directory

mkdir .data 

# start the database - postgreSQL
docker compose up -d
