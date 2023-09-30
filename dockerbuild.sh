#!/bin/bash

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\api-gateway
docker build api-gateway:latest .
docker tag api-gateway:latest psanger/api-gateway:latest
docker push psanger/api-gateway:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\authentication-service
docker build authentication-service:latest .
docker tag authentication-service:latest psanger/authenticatione:latest
docker push psanger/authentication-service:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\carte_e_mazzi-service
docker build carte-e-mazzi-service:latest .
docker tag carte-e-mazzi-service:latest psanger/carte-e-mazzi-service:latest
docker push psanger/carte-e-mazzi-service:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\collectdata-service
docker build collectdata-service:latest .
docker tag collectdata-service:latest psanger/collectdata-service:latest
docker push psanger/collectdata-service:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\marketplace-service
docker build marketplace-service:latest .
docker tag marketplace-service:latest psanger/marketplace-service:latest
docker push psanger/marketplace-service:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\home-service
docker build home-service:latest .
docker tag home-service:latest psanger/home-service:latest
docker push psanger/home-service:latest

cd C:\Users\pietr\Desktop\Uni\TAAS\MusicDraftBackend\matchmaking-service
docker build matchmaking-service:latest .
docker tag matchmaking-service:latest psanger/matchmaking-service:latest
docker push psanger/matchmaking-service:latest

docker-compose up

