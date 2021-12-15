# Docker

Docker is an open source containerization platform.
Containers are isolated from one another and bundle their own software, libraries and configuration files; 
They can communicate with each other through well-defined channels.

#Prerequistes

- Have docker installed in the machine that will use docker; 
- In this case for dockerfile it was necessary to have first implemented spring and angular; 
- Last one, it's necessary to have the angular application to dockerize.

#Check docker version machine

- The following docker-machine CLI command can be run to check the Docker version machine details.
             $ docker -v

#Create dockerfile backend
    FROM maven:3.8.4-openjdk-17 AS build
    WORKDIR /ads
    COPY adsbe/pom.xml .
    # build all dependencies
    RUN mvn dependency:go-offline -B
    # copy source files and build the project
    COPY adsbe/src src
    RUN mvn -f pom.xml -DskipTests package
    
    FROM openjdk:17.0-oraclelinux7
    COPY --from=build /ads/target/ads-0.0.1-SNAPSHOT.jar /usr/local/lib/ads.jar
    EXPOSE 8080
    ENTRYPOINT ["java","-jar","/usr/local/lib/ads.jar"]

#Create dockerfile frontend
    FROM node:16-alpine3.12 as build
    
    WORKDIR /usr/local/app
    
    COPY adsfe/. /usr/local/app/
    
    RUN npm install
    
    # Generate the build of the application
    RUN npm run build --env=prod
    
    FROM nginx:1.21.4-alpine
    
    # Copy the build output to replace the default nginx contents.
    COPY --from=build /usr/local/app/dist/adsfe /usr/share/nginx/html

#Dockerfile Compose
    Tool for defining and running multi-container Docker applications. 
    Define the services that make up your app in docker-compose.
    In this case, the dockerfile compose will be used to integrate the be dockerfile and fe dockerfile.

# File - Dockerfile compose
    version: '3.3'

    services:
        adsbe:
            image: 'jmam93/ads-be:latest'
            container_name: 'adsbe'
            ports:
              - '8080:8080'
            networks:
              - app-network
        adsfe:
            image: 'jmam93/ads-fe:latest'
            container_name: 'adsfe'
            ports:
              - '80:80'
            depends_on:
              - adsbe
            networks:
              - app-network
    networks:
        app-network:
            driver: bridge
#Command to create the docker images
    docker build -t jmam93/ads-fe:latest .

#Command to effective run the docker container
    docker run -p 4200:4200 --name angular-containeerr angular-app
    where angular-container refers to the container
    -p refers to the port 

#Last step
    Check if the web page of frontend is communicating successfully with docker by the url given 
    by the last command in the final step

[Back](../README.md)
