# สเต็ปที่ 1: ใช้ Maven จำลองเพื่อ Build โค้ด
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# สเต็ปที่ 2: นำไฟล์ .jar ที่ได้มารันบน Java 17
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]