FROM adoptopenjdk/maven-openjdk11 AS MAVEN_BUILD
MAINTAINER Maxim Dorofeev
COPY . /build/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
EXPOSE 8080
WORKDIR /app
COPY --from=MAVEN_BUILD /build/start.sh /app/start.sh
COPY --from=MAVEN_BUILD /build/auth-service/target/lesson3-auth-service-1.0.jar /app/auth-service.jar

CMD ["bash", "/app/start.sh"]