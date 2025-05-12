FROM ibm-semeru-runtimes:open-21.0.6_7-jre-jammy
#VOLUME /tmp
#ARG JAVA_OPTS
#ENV JAVA_OPTS=$JAVA_OPTS
ENV SPRING_PROFILES_ACTIVE=default
ENV DB_URL=jdbc:postgresql://localhost:5432/postgres?currentSchema=data01
ENV DB_DRIVER=org.postgresql.Driver
ENV DB_USERNAME=user01
# Removed sensitive data from ENV command. Pass DB_PASSWORD as a runtime environment variable.
# ENV DB_PASSWORD=
WORKDIR /apps
COPY target/user-auth-n-0.0.1-SNAPSHOT.jar user-auth-n-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT [ "java", "-jar", "user-auth-n-0.0.1-SNAPSHOT.jar" ]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar springsecurity.jar
