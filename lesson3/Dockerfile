FROM maven:3.5.2-jdk-8-alpine AS MAVEN_BUILD
MAINTAINER Maxim Dorofeev
COPY pom.xml /build/
COPY src /build/src/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:8-jre-alpine
EXPOSE 8080
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/finance-app-1.0.jar /app/
ENTRYPOINT ["java", "-Dspring.profiles.active=db-h2mem", "-jar", "finance-app-1.0.jar"]