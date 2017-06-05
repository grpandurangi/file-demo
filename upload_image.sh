#!/bin/bash

docker login -e grpandurangi@gmail.com -u _json_key -p "$(cat /tmp/key.json)" https://gcr.io
docker build -t gcr.io/csd-automation/filedemo/file-demo:latest .
docker push gcr.io/csd-automation/filedemo/file-demo:latest
