#!/bin/bash

#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/api-gateway
#docker build -t api-gateway:latest .
#docker tag api-gateway:latest ziomike97/api-gateway:latest
#docker push ziomike97/api-gateway:latest

cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/authentication-service
docker build -t authentication-service:latest .
docker tag authentication-service:latest ziomike97/authentication-service:latest
docker push ziomike97/authentication-service:latest

#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/carte_e_mazzi-service
#docker build -t carte-e-mazzi-service:latest .
#docker tag carte-e-mazzi-service:latest ziomike97/carte-e-mazzi-service:latest
#docker push ziomike97/carte-e-mazzi-service:latest
#
#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/collectdata-service
#docker build -t collectdata-service:latest .
#docker tag collectdata-service:latest ziomike97/collectdata-service:latest
#docker push ziomike97/collectdata-service:latest
#
#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/marketplace-service
#docker build -t marketplace-service:latest .
#docker tag marketplace-service:latest ziomike97/marketplace-service:latest
#docker push ziomike97/marketplace-service:latest
#
#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/home-service
#docker build -t home-service:latest .
#docker tag home-service:latest ziomike97/home-service:latest
#docker push ziomike97/home-service:latest
#
#cd C:/Users/miky9/IdeaProjects/spikes/MusicDraftBackend/matchmaking-service
#docker build -t matchmaking-service:latest .
#docker tag matchmaking-service:latest ziomike97/matchmaking-service:latest
#docker push ziomike97/matchmaking-service:latest


# PROVA A SALVARTI ANCHE L'IMMAGINE DI RABBITMQ in questo modo anche le code che sono state già create dovrebbero rimanere..