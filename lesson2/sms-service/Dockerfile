FROM openjdk:11-jre-slim
VOLUME /tmp
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/lesson2-service-sms-1.0.jar /app/sms.jar
ENTRYPOINT ["java", "-jar", "/app/sms.jar"]