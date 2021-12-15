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
