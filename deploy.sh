#!/bin/bash

docker login -e grpandurangi@gmail.com -u _json_key -p "$(cat /tmp/key.json)" https://gcr.io
docker build -t gcr.io/csd-automation/filedemo/file-demo:latest .
docker push gcr.io/csd-automation/filedemo/file-demo:latest
gcloud config set compute/zone us-east1-d
gcloud config set project csd-automation
gcloud container clusters create file-demo --num-nodes 1
/usr/lib/google-cloud-sdk/bin/kubectl  run my-file-demo --replicas=2 --labels="run=file-demo-lb-ex" --image=gcr.io/csd-automation/filedemo/file-demo:latest --port=9090
sleep 60
/usr/lib/google-cloud-sdk/bin/kubectl get deployments my-file-demo
/usr/lib/google-cloud-sdk/bin/kubectl describe deployments my-file-demo
/usr/lib/google-cloud-sdk/bin/kubectl get pods
sleep 30
/usr/lib/google-cloud-sdk/bin/kubectl get replicasets
/usr/lib/google-cloud-sdk/bin/kubectl describe replicasets
/usr/lib/google-cloud-sdk/bin/kubectl expose deployment my-file-demo --type=LoadBalancer --name=my-file-demo-service
/usr/lib/google-cloud-sdk/bin/kubectl describe service my-file-demo-service
sleep 120
/usr/lib/google-cloud-sdk/bin/kubectl describe service my-file-demo-service
