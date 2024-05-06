FROM openjdk:latest

WORKDIR /app

COPY target/scrapper.jar /app

EXPOSE 8080
EXPOSE 8081

CMD ["java", "-jar", "scrapper.jar"]
