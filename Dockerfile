FROM openjdk:8
ADD target/credit-service.jar credit-service.jar
EXPOSE 8085
ENTRYPOINT ["java","-jar","credit-service.jar"]