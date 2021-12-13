#!/bin/bash

if [[ $# -eq 0 ]] ; then
    echo "No version argument supplied."
    exit 1
fi

VERSION_TAG=$1
LATEST_IMAGE_NAME="jmam93/ads-be:latest"
CURRENT_IMAGE_NAME="jmam93/ads-be:$VERSION_TAG"

docker build \
    --file docker/Dockerfile \
    --tag "$CURRENT_IMAGE_NAME" .

docker tag "$CURRENT_IMAGE_NAME" $LATEST_IMAGE_NAME

if [ $? -ne 0 ]; then
    echo FAIL
    exit 1
fi

docker login
docker push "$CURRENT_IMAGE_NAME"
docker push $LATEST_IMAGE_NAME

if [ $? -ne 0 ]; then
    echo FAIL
    exit 1
fi

echo "$CURRENT_IMAGE_NAME"
echo "$LATEST_IMAGE_NAME"

LATEST_IMAGE_NAME="jmam93/ads-fe:latest"
CURRENT_IMAGE_NAME="jmam93/ads-fe:$VERSION_TAG"


docker build \
    --file docker/Dockerfile \
    --tag "$CURRENT_IMAGE_NAME" .

docker tag "$CURRENT_IMAGE_NAME" $LATEST_IMAGE_NAME

if [ $? -ne 0 ]; then
    echo FAIL
    exit 1
fi

docker login
docker push "$CURRENT_IMAGE_NAME"
docker push $LATEST_IMAGE_NAME

if [ $? -ne 0 ]; then
    echo FAIL
    exit 1
fi

echo "$CURRENT_IMAGE_NAME"
echo "$LATEST_IMAGE_NAME"
