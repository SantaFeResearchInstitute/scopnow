#!/usr/bin/bash

# usage
#  $ sh buildAll.sh production

BASE_DIR=$(cd $(dirname $0); pwd)
ENV=$1 # staging or production

echo "Start a full build of the scop-sensors-api"
date

# sensors build
BACK_END_MODULE=scop-sensors-api-0.0.1-SNAPSHOT.war
sh $BASE_DIR/scop-sensors-api/build.sh $BASE_DIR/scop-sensors-api/ $ENV $BACK_END_MODULE

echo "Start a full build of the scop-sensors-api"
date
