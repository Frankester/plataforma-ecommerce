# Multi-stage build
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
# Enable faster dependency resolve by copying only pom first
RUN mvn -q -e -DskipTests dependency:go-offline
COPY src ./src
RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre
ENV JAVA_OPTS=""
WORKDIR /app
# Copy fat jar
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8765
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
