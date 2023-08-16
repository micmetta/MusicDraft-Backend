FROM openjdk:18
WORKDIR /opt
ENV PORT 8081
EXPOSE 8081
COPY target/*.jar /opt/app.jar
ENTRYPOINT exec java $JAVA_OPTS -jar app.jar

#FROM openjdk:18
##WORKDIR /opt
##ENV PORT 8081
#EXPOSE 8081
##COPY target/*.jar /opt/app.jar
##ENTRYPOINT exec java $JAVA_OPTS -jar app.jar
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#CMD ["java", "-jar", "app.jar"]