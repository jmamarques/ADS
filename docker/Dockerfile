FROM maven:3.6.0-jdk-11-slim AS build
WORKDIR /ads
COPY adsbe/pom.xml .
# build all dependencies
RUN mvn dependency:go-offline -B
# copy source files and build the project
COPY adsbe/src src
RUN mvn -f ads/pom.xml -DskipTests package

FROM openjdk:11-jre-slim
RUN apt-get update; apt-get install -y fontconfig libfreetype6
COPY --from=build /ads/target/ads-1.0.0.jar /usr/local/lib/ads.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/ads.jar"]
