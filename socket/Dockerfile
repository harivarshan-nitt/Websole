FROM openjdk:8-jdk-alpine3.7 AS builder
RUN java -version

COPY . /usr/src/socket/
WORKDIR /usr/src/socket/
RUN apk --no-cache add maven
RUN mvn --version
RUN mvn package

FROM openjdk:8-jre-alpine3.7
WORKDIR /root/
COPY --from=builder /usr/src/socket/target/socket-0.0.1-SNAPSHOT.jar .

ENTRYPOINT ["java", "-jar", "./socket-0.0.1-SNAPSHOT.jar"]