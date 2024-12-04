#!/bin/bash

# Build the Docker image
sudo docker build -t pmshree .

# Stop and remove existing container if it is running
sudo docker stop pmshree || true
sudo docker rm pmshree || true

# Run the new container with a mounted volume for the keystore
sudo docker run --name pmshree -p 8447:8447 \
  -v /etc/ssl/springboot/pmshribahlolpur.p12:/etc/ssl/springboot/pmshribahlolpur.p12 \
  pmshree