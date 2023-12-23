FROM openjdk:21
EXPOSE 8080
WORKDIR /cars-01-web

ADD src/main/java/com/app/infrastructure/api/data data
ADD target/cars_01.jar cars_01.jar
ENTRYPOINT ["java", "-jar", "cars_01.jar"]