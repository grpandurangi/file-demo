#!/bin/bash

gcloud config set compute/zone us-east1-d
gcloud config set project csd-automation

gcloud container clusters describe file-demo

if [[ "$?" -eq "0" ]]; then


/usr/lib/google-cloud-sdk/bin/kubectl apply -f deployment.yml
/usr/lib/google-cloud-sdk/bin/kubectl get service my-file-demo-service

if [[ "$?" -eq "0" ]]; then

/usr/lib/google-cloud-sdk/bin/kubectl get pods
/usr/lib/google-cloud-sdk/bin/kubectl set image deployment/my-file-demo my-file-demo=gcr.io/csd-automation/filedemo/file-demo:latest
/usr/lib/google-cloud-sdk/bin/kubectl set image deployment/my-file-demo my-file-demo=gcr.io/csd-automation/filedemo/file-demo
/usr/lib/google-cloud-sdk/bin/kubectl get pods

else

/usr/lib/google-cloud-sdk/bin/kubectl expose deployment my-file-demo --type=LoadBalancer --name=my-file-demo-service --external-ip="35.190.34.116"

fi

else

echo "Google Container Engine is not created";
exit 1;

fi


