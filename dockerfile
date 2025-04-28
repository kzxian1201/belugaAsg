FROM maven:3.9.9-eclipse-temurin-18 AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

CMD ["mvn", "test"]
