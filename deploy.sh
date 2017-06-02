#!/bin/bash

docker login -e grpandurangi@gmail.com -u _json_key -p "$(cat /tmp/key.json)" https://gcr.io
docker build -t gcr.io/csd-automation/filedemo/file-demo:latest .
docker push gcr.io/csd-automation/filedemo/file-demo:latest
gcloud config set compute/zone us-east1-d
gcloud config set project csd-automation

#/usr/lib/google-cloud-sdk/bin/kubectl delete deployments my-file-demo
kubectl get pods
kubectl set image deployment/my-file-demo my-file-demo=gcr.io/csd-automation/filedemo/file-demo:latest
kubectl set image deployment/my-file-demo my-file-demo=gcr.io/csd-automation/filedemo/file-demo
kubectl get pods
