FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /books-app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /books-app
COPY --from=build /books-app/target/*.jar /books-app/BooksApplication.jar
EXPOSE 8080
CMD ["java", "-jar", "BooksApplication.jar"]