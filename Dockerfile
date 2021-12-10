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

FROM node:16-alpine3.12 as buildfe

WORKDIR /usr/local/app

COPY adsfe/. /usr/local/app/

RUN npm install

# Generate the build of the application
RUN npm run build

FROM nginx:1.21.4-alpine

# Copy the build output to replace the default nginx contents.
COPY --from=buildfe /usr/local/app/dist/adsfe /usr/share/nginx/html
