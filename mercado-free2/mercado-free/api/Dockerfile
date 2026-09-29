# Etapa 1: construir el JAR con Maven y JDK 17
FROM maven:3.9-eclipse-temurin-17 AS build

# Directorio de trabajo dentro del contenedor de build
WORKDIR /app

# Copiamos el pom primero (cache de dependencias)
COPY pom.xml .

# Copiamos el código fuente
COPY src ./src

# Construimos el JAR (sin tests para que sea más rápido)
RUN mvn clean package -DskipTests

# Etapa 2: imagen final liviana con solo el JAR
FROM eclipse-temurin:17-jdk

# Directorio de trabajo en la imagen final
WORKDIR /app

# Copiamos el JAR generado desde la etapa "build"
COPY --from=build /app/target/*.jar app.jar

# Exponemos el puerto donde corre Spring Boot
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]
