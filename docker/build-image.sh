#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo "No version argument supplied."
    exit 1
fi

VERSION_TAG=$1 

docker build \
    --build-arg USERNAME=$(id -un) \
    --build-arg USERID=$(id -u) \
    --build-arg GROUPID=$(id -g) \
    --file docker/Dockerfile \
    --tag ads:$VERSION_TAG .

docker tag ads:$VERSION_TAG ads:latest

echo ads:$VERSION_TAG
echo ads:latest

docker build \
    --build-arg USERNAME=$(id -un) \
    --build-arg USERID=$(id -u) \
    --build-arg GROUPID=$(id -g) \
    --file docker/DockerfileFe \
    --tag adsfe:$VERSION_TAG .

docker tag adsfe:$VERSION_TAG adsfe:latest

echo adsfe:$VERSION_TAG
echo adsfe:latest
