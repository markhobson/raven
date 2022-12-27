#!/bin/bash

# Install Docker
apt-get update
apt-get install -y docker.io

# Run Raven server
docker run --name raven-server -p80:80 -d markhobson/raven-server
