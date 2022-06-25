FROM openjdk:11
MAINTAINER vivacon-author
COPY target/Vivacon-0.0.1-SNAPSHOT.jar vivacon.jar
ENTRYPOINT ["java","-jar","vivacon.jar"]