#!/bin/bash

docker run -it --rm \
        -p 3306:3306 \
        -e MYSQL_DATABASE=db \
        -e MYSQL_ALLOW_EMPTY_PASSWORD=yes \
        mysql:8
        