FROM maven:3.9.9-eclipse-temurin AS builder

WORKDIR /app

COPY . .

RUN mvn clean install

CMD ["mvn", "test"]
