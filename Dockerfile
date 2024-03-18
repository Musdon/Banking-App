FROM openjdk:21-jdk-slim
COPY target/BankingApp-0.0.1-SNAPSHOT.jar BankingApp.jar


ENTRYPOINT ["java", "-jar", "BankingApp.jar"]