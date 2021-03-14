#!/bin/bash
read -p "[start] [stop]: "  command

if [ "${command}" == "start" ]
then
  cd ./docker-compose
  docker-compose up -d
  sleep 5
  exit
fi

if [ "${command}" == "stop" ]
then
  cd ./docker-compose
  docker-compose down
  exit
fi