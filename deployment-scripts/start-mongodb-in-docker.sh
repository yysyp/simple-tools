#! /bin/bash -eu


#docker run --name mysql57x -e MYSQL_ROOT_PASSWORD=root -d --add-host=host.docker.internal:host-gateway -p 3306:3306 mysql57x

docker run -d -p 28028:28028 --name simple-mongo mongo:latest
#docker exec -it simple-mongo mongo
