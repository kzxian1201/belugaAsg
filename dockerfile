FROM maven:3.9.3-eclipse-temurin-17 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

CMD ["mvn", "test"]
