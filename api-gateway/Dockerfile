FROM openjdk:18
WORKDIR /opt
ENV PORT 9095
EXPOSE 9095
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar