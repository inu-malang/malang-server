FROM openjdk:17-alpine
VOLUME /tmp
COPY /build/libs/malang-0.0.1-SNAPSHOT.jar malang.jar
ENTRYPOINT ["java","-jar","malang.jar"]
EXPOSE 8080