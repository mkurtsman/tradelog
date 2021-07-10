FROM adoptopenjdk/openjdk11:latest
ARG JAR_FILE=build/libs/tradelog-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
RUN mkdir -p /data/backup
COPY data/*.htm /data/backup/
EXPOSE 8000
ENTRYPOINT ["java","-jar","/app.jar"]