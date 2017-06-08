#!/bin/bash

gcloud config set compute/zone us-east1-d
gcloud config set project csd-automation

gcloud container clusters describe file-demo

if [[ "$?" -eq "0" ]]; then


/usr/lib/google-cloud-sdk/bin/kubectl apply -f deployment.yml
/usr/lib/google-cloud-sdk/bin/kubectl get service my-file-demo-service

if [[ "$?" -ne "0" ]]; then

/usr/lib/google-cloud-sdk/bin/kubectl apply -f service.yml
#/usr/lib/google-cloud-sdk/bin/kubectl expose deployment my-file-demo --type=LoadBalancer --name=my-file-demo-service ;


fi

else

echo "Google Container Engine is not created";
exit 1;

fi


