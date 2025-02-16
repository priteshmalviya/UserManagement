FROM maven:3-eclipse-temurin-17 AS build
COPY • •
RUN men clean package -DskipTests
FROM eclipse-temurin: 17-alpine
COPY -- from=build /target/*-jar demo. jar
EXPOSE 8080
ENTRYPOINT ["java","-jar", "demo. jar"]
