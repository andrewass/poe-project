FROM openjdk:8

ADD build/libs/poe.project-0.0.1-SNAPSHOT.jar poe.project-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "poe.project-0.0.1-SNAPSHOT.jar"]

