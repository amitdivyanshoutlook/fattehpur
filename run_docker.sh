#!/bin/bash

# Build the Docker image
sudo docker build -t fattehpur .

# Stop and remove existing container if it is running
sudo docker stop fattehpur || true
sudo docker rm fattehpur || true

# Run the new container with a mounted volume for the keystore
sudo docker run --name fattehpur -p 8448:8448 \
  -v /etc/ssl/springboot/fattehpur.p12:/etc/ssl/springboot/fattehpur.p12 \
  fattehpur