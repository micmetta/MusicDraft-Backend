FROM openjdk:18
WORKDIR /opt
ENV PORT 9091
EXPOSE 9091
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar