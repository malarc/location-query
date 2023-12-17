#
# Build stage
#
FROM maven:3.8.4-openjdk-11-slim AS build
COPY /service /home/app/service
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package

#
# Package stage
#
FROM openjdk:11-jdk-oracle
COPY --from=build /home/app/service/target/*.jar /usr/local/lib/location-service.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/usr/local/lib/location-service.jar"]
