FROM adoptopenjdk/maven-openjdk11 AS MAVEN_BUILD
MAINTAINER Maxim Dorofeev
COPY . /build/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
EXPOSE 8080
WORKDIR /app
COPY --from=MAVEN_BUILD /build/start.sh /app/start.sh
COPY --from=MAVEN_BUILD /build/central-service/target/lesson2-central-1.0.jar /app/central.jar
COPY --from=MAVEN_BUILD /build/sms-service/target/lesson2-service-sms-1.0.jar /app/sms.jar
COPY --from=MAVEN_BUILD /build/email-service/target/lesson2-service-email-1.0.jar /app/email.jar
CMD ["bash", "/app/start.sh"]