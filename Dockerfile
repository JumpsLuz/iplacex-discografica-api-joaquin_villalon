# ETAPA/STAGE 1 GENERAR

FROM gradle:jdk21 as builder
WORKDIR /app
COPY ./build.gradle .
COPY ./settings.gradle .
COPY src ./src
RUN gradle build --no-daemon

# ETAPA/STAGE 2 EJECUTAR

FROM openjdk:21-jdk-slim
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar discografia-1.jar
EXPOSE 443
CMD ["java", "-jar", "discografia-1.jar"]