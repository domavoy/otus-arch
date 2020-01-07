FROM openjdk:11-jre-slim
VOLUME /tmp
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/lesson2-service-email-1.0.jar /app/email.jar
ENTRYPOINT ["java", "-jar", "/app/email.jar"]