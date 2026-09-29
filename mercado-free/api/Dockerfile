# Paso 1: imagen de Maven para construir el proyecto
FROM maven:3.8.3-openjdk-17 AS builder

# Paso 2: Se Copia los archivos del proyecto
COPY src /home/app/src
COPY pom.xml /home/app

# Paso 3: Se Construye el proyecto y empaqueta la aplicación
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip=true

# Paso 4: Se Usa la imagen oficial de OpenJDK 17 para la imagen final
#FROM openjdk:17-jdk
FROM eclipse-temurin:17-jdk-jammy

# Paso 5: Se Copia el archivo JAR del 'builder' al nuevo contenedor
COPY --from=builder /home/app/target/*.jar /app/mercado-free-api.jar

# Paso 6: Se Establece el directorio de trabajo
WORKDIR /app

# Paso 7: Se Expone el puerto en el que tu aplicación escuchará
EXPOSE 8080

# Paso 8: Define el comando para ejecutar tu aplicación
ENTRYPOINT ["java", "-jar", "/app/mercado-free-api.jar"]