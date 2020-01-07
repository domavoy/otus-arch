FROM openjdk:11-jre-slim
VOLUME /tmp
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/lesson2-central-1.0.jar /app/central.jar
ENTRYPOINT ["java", "-jar", "/app/central.jar"]