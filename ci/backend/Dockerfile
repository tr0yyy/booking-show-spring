FROM openjdk:17

WORKDIR /app

COPY src/ .
COPY pom.xml .
COPY mvnw .
COPY mvnw.cmd .
COPY .mvn/ .mvn/

RUN ./mvnw package

EXPOSE 8080

CMD ["java", "-jar", "target/bookingshow-0.0.1-SNAPSHOT.jar"]