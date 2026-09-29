# api-uf-dolar-project/Dockerfile

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

# OJO: ajusta el nombre del jar si es distinto
COPY target/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]




# ===== Build stage =====
#FROM maven:3.9-eclipse-temurin-17 AS build
#WORKDIR /workspace

# Cache de dependencias
#COPY pom.xml ./
#RUN mvn -B -q -DskipTests dependency:go-offline

# Código fuente
#COPY src ./src
#RUN mvn -B -q -DskipTests package

# ===== Runtime stage =====
#FROM eclipse-temurin:17-jre-jammy
#WORKDIR /app

# Copiamos el jar generado en el stage de build
#COPY --from=build /workspace/target/*.jar /app/app.jar

#EXPOSE 8080
#ENV JAVA_OPTS=""

#ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar /app/app.jar"]
