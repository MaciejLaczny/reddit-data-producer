FROM maven:3.8.3-openjdk-17 AS build
COPY src /usr/src/app/src
COPY pom.xml /usr/src/app
RUN mvn -f /usr/src/app/pom.xml clean package

FROM gcr.io/distroless/java17-debian11
COPY --from=build /usr/src/app/target/reddit-data-producer.jar /usr/app/reddit-data-producer.jar
ENTRYPOINT ["java","-jar","/usr/app/reddit-data-producer.jar"]
