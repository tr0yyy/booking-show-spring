FROM openjdk:17

WORKDIR /app

COPY src/ src/
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/

RUN chmod +x ./mvnw && ./mvnw package

EXPOSE 8761

CMD ["./mvnw", "spring-boot:run"]