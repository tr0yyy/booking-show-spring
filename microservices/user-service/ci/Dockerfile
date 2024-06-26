FROM openjdk:17

WORKDIR /app

COPY src/ src/
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/

EXPOSE 8081

CMD ["./mvnw", "spring-boot:run"]