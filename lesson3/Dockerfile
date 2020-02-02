FROM adoptopenjdk/maven-openjdk11 AS MAVEN_BUILD
MAINTAINER Maxim Dorofeev
COPY . /build/
WORKDIR /build/
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim
EXPOSE 8080
EXPOSE 8081
EXPOSE 8082
EXPOSE 8083
EXPOSE 8084
WORKDIR /app
COPY --from=MAVEN_BUILD /build/start.sh /app/start.sh
COPY --from=MAVEN_BUILD /build/auth-service/target/lesson3-auth-service-1.0.jar /app/auth-service.jar
COPY --from=MAVEN_BUILD /build/finance-service/target/lesson3-finance-service-1.0.jar /app/finance-service.jar
COPY --from=MAVEN_BUILD /build/budget-service/target/lesson3-budget-service-1.0.jar /app/budget-service.jar
COPY --from=MAVEN_BUILD /build/repeated-payment-service/target/lesson3-repeated-payment-service-1.0.jar /app/repeated-payment-service.jar
COPY --from=MAVEN_BUILD /build/scheduler-service/target/lesson3-scheduler-service-1.0.jar /app/scheduler-service.jar

CMD ["bash", "/app/start.sh"]