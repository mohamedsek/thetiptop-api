FROM maven:3.8.5-openjdk-17-slim AS MAVEN_BUILD
COPY . /build/
WORKDIR /build/
RUN mvn clean install -Dmaven.test.skip=true

FROM openjdk:17.0.1-jdk-slim
WORKDIR /app
COPY --from=MAVEN_BUILD /build/target/*.jar /app/app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]