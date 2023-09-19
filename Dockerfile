FROM openjdk

WORKDIR /app

COPY target/app-0.0.1-SNAPSHOT.jar /app/sis-bank.jar

EXPOSE 8080

ENTRYPOINT [ "java", "-jar", "sis-bank.jar" ]