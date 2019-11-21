FROM openjdk:8-jdk-alpine

MAINTAINER Rafal.Kotusiewicz@tt.com.pl

COPY build/libs/*.jar /opt
ENTRYPOINT ["java","-jar", "/opt/github-repositories-0.0.1-SNAPSHOT.jar"]
EXPOSE 5150