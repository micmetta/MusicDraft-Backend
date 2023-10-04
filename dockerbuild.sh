#!/bin/bash

#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/api-gateway
#docker build -t api-gateway:latest .
#docker tag api-gateway:latest ziomike97/api-gateway:latest
#docker push ziomike97/api-gateway:latest

cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraft-Frontend
docker build -t musicdraft-frontend:latest .
docker tag musicdraft-frontend:latest psanger/musicdraft-frontend:latest
docker push psanger/musicdraft-frontend:latest

#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/authentication-service
#docker build -t authentication-service:latest .
#docker tag authentication-service:latest psanger/authentication-service:latest
#docker push psanger/authentication-service:latest
#
#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/carte_e_mazzi-service
#docker build -t carte-e-mazzi-service:latest .
#docker tag carte-e-mazzi-service:latest psanger/carte-e-mazzi-service:latest
#docker push psanger/carte-e-mazzi-service:latest
#
#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/collectdata-service
#docker build -t collectdata-service:latest .
#docker tag collectdata-service:latest psanger/collectdata-service:latest
#docker push psanger/collectdata-service:latest
#
#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/marketplace-service
#docker build -t marketplace-service:latest .
#docker tag marketplace-service:latest psanger/marketplace-service:latest
#docker push psanger/marketplace-service:latest
#
#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/home-service
#docker build -t home-service:latest .
#docker tag home-service:latest psanger/home-service:latest
#docker push psanger/home-service:latest
#
#cd C:/Users/pietr/Desktop/Uni/TAAS/MusicDraftBackend/matchmaking-service
#docker build -t matchmaking-service:latest .
#docker tag matchmaking-service:latest psanger/matchmaking-service:latest
#docker push psanger/matchmaking-service:latest



