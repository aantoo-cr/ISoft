# ===== Build stage =====
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /workspace

# copiamos pom y bajamos dependencias
COPY pom.xml ./
RUN mvn -B -q -DskipTests dependency:go-offline

# codigo fuente y construir el jar
COPY src ./src
RUN mvn -B -q -DskipTests package

# ===== Runtime stage =====
FROM eclipse-temurin:17-jre-jammy
WORKDIR /app

# jar construido en el stage anterior
COPY --from=build /workspace/target/*.jar /app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
