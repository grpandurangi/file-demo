#!/bin/bash

gcloud config set compute/zone us-east1-d
gcloud config set project csd-automation
#gcloud container clusters create file-demo --num-nodes 1

gcloud container clusters describe file-demo  2>/dev/null 2>&1

if [[ "$?" -ne "0" ]]; then

gcloud container clusters create file-demo --enable-autoscaling --min-nodes=1 --max-nodes=3

fi
