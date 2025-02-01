FROM openjdk:21-jdk-alpine

# Copiar el archivo .jar generado por Gradle
COPY build/libs/garden-0.0.1-SNAPSHOT.jar app.jar

# Configurar el comando para ejecutar el .jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
