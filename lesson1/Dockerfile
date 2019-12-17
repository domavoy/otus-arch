FROM openjdk:11-jre-slim
VOLUME /tmp
EXPOSE 8082
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/finance-app-1.0.jar /app/finance.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-Dspring.profiles.active=h2mem", "-jar", "/app/finance.jar"]