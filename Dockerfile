FROM mcr.microsoft.com/openjdk/jdk:17-ubuntu
LABEL authors="svku0919"
ARG JAR_FILE=target/cash-app.jar
COPY ${JAR_FILE} cash-app.jar
ENTRYPOINT ["java","-jar","/cash-app.jar"]